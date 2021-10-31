package com.tuwaiq.todolistapp

import android.annotation.SuppressLint
import android.app.Application
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.edit_layout.*
import kotlinx.android.synthetic.main.recyclerview_item.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fapButton: FloatingActionButton
    private lateinit var mainVM: TaskVM
    private lateinit var taskList: List<ToDo>
    private val requestCodeVar = 2
    lateinit var updatedTask: ToDo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView= findViewById(R.id.recyclerview)
        recyclerView.layoutManager=  LinearLayoutManager(this)

        mainVM= ViewModelProvider(this).get(TaskVM::class.java)
        mainVM.getAllTasks().observe(this, Observer {
            recyclerView.adapter = ToDoRecyclerAdapter(it, TaskVM(Application()))
            taskList= it
        })


        val taskTouchHelper= ItemTouchHelper(simpleCallback)
        taskTouchHelper.attachToRecyclerView(recyclerView)

        fapButton= findViewById(R.id.fab)
        fapButton.setOnClickListener {
            val i= Intent(this, NewTaskActivity::class.java)
            startActivityForResult(i, requestCodeVar)
        }


    }

    private var simpleCallback= object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition
            mainVM= ViewModelProvider(this@MainActivity).get(TaskVM::class.java)
            updatedTask = taskList.get(position)
            val cal = Calendar.getInstance()
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            val simpleDateFormat1= SimpleDateFormat("dd/MM/yyyy")
            val localDate= simpleDateFormat1.format(cal.time)
            val simpleDateFormat= SimpleDateFormat("dd/MM/yyyy")
            val inflater = LayoutInflater.from(this@MainActivity)
            val v = inflater.inflate(R.layout.edit_layout, null)
            val newCurrentDate = v.findViewById<TextView>(R.id.tvNewCurrentDate)
            val editTask = v.findViewById<EditText>(R.id.newEditTask)
            val editDate = v.findViewById<TextView>(R.id.newEditDate)
            val editInfo= v.findViewById<EditText>(R.id.new_desc)

            newCurrentDate.text = simpleDateFormat.format(cal.time)
            editDate.setOnClickListener {
                DatePickerDialog(this@MainActivity, { _, year, month, day ->
                    editDate.text = "$day/${month + 1}/$year"
                }, year, month, day).show()
            }
            val builder = AlertDialog.Builder(this@MainActivity)




            when(direction){
                ItemTouchHelper.LEFT -> {
                    mainVM.deleteTask(updatedTask).observe(this@MainActivity, {
                        mainVM.getAllTasks().observe(this@MainActivity, {
                            recyclerView.adapter =
                                ToDoRecyclerAdapter(it, TaskVM(Application()))
                            taskList = it
                        })
                    })
                }

                ItemTouchHelper.RIGHT -> {

                        builder.setCancelable(true)
                        builder.setView(v)
                        builder.setNegativeButton("cancel") { _, _ ->

                            Toast.makeText(this@MainActivity, "You Didn't Edit Your Task", Toast.LENGTH_LONG).show()
                        }
                        builder.setPositiveButton("update") { _, _ ->
                            if (editTask.text.isNotEmpty()) {
                                updatedTask.currentDate = newCurrentDate.text.toString()
                                updatedTask.task = editTask.text.toString()
                                updatedTask.desc = editInfo.text.toString()
                                updatedTask.date = editDate.text.toString()
                                updatedTask.completed = false
                                mainVM.updateTask(updatedTask).observe(this@MainActivity, {
                                    mainVM.getAllTasks().observe(this@MainActivity, {
                                        recyclerView.adapter =
                                            ToDoRecyclerAdapter(it, TaskVM(Application()))
                                        taskList = it
                                    })
                                })
                            } else {
                                Toast.makeText(this@MainActivity, "You have to write new task", Toast.LENGTH_LONG).show()
                            }
                        }
                        builder.show()

                }

            }
            mainVM.getAllTasks().observe(this@MainActivity, {
                recyclerView.adapter =
                    ToDoRecyclerAdapter(it, TaskVM(Application()))
                taskList = it
            })
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mainVM= ViewModelProvider(this).get(TaskVM::class.java)
        mainVM.getAllTasks().observe(this, {
            recyclerView.adapter = ToDoRecyclerAdapter(it,TaskVM(Application()))
            taskList= it
        })
        if (resultCode == RESULT_OK) {
            if (requestCodeVar == requestCode) {

                data?.getParcelableExtra<ToDo>("task")?.let { toDo ->
                    mainVM.insertTask(toDo).observe(this@MainActivity, {
                        mainVM.getAllTasks().observe(this@MainActivity, {
                            recyclerView.adapter = ToDoRecyclerAdapter(it,TaskVM(Application()))
                            taskList= it })
                    })
                }
            }
        }
    }
}