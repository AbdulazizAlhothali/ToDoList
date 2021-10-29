package com.tuwaiq.todolistapp

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.edit_layout.*
import kotlinx.android.synthetic.main.recyclerview_item.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fapButton: FloatingActionButton

    private val requestCodeVar = 2
    var taskList = mutableListOf<ToDo>()


    lateinit var deletedTask: ToDo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        recyclerView= findViewById(R.id.recyclerview)
        recyclerView.layoutManager=  LinearLayoutManager(this)
        recyclerView.adapter= ToDoRecyclerAdapter(taskList)
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

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            var position = viewHolder.adapterPosition

            when(direction){
                ItemTouchHelper.LEFT -> {
                    deletedTask = taskList.get(position)
                        taskList.removeAt(position)
                    recyclerView.adapter!!.notifyItemRemoved(position)
                }
              ItemTouchHelper.RIGHT -> {
                  val inflter = LayoutInflater.from(this@MainActivity)
                  val v = inflter.inflate(R.layout.edit_layout,null)

                  val editTask = v.findViewById<EditText>(R.id.newEditTask)
                  val editDate = v.findViewById<TextView>(R.id.newEditDate)
                  val cal = Calendar.getInstance()
                  val day = cal.get(Calendar.DAY_OF_MONTH)
                  val month = cal.get(Calendar.MONTH)
                  val year = cal.get(Calendar.YEAR)
                  editDate.setOnClickListener{
                      DatePickerDialog(this@MainActivity, DatePickerDialog.OnDateSetListener {
                              view, year, month, day ->
                          editDate.setText("$day/${month+1}/$year")
                      }, year,month,day).show()
                  }

                  val builder = AlertDialog.Builder(this@MainActivity)
                  builder.setTitle("Edit Task")
                  builder.setCancelable(true)
                  builder.setView(v)
                  builder.setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, wich ->
                      Toast.makeText(this@MainActivity,"You Didn't Edit Your Task",Toast.LENGTH_LONG).show()
                      recyclerView.adapter!!.notifyDataSetChanged()
                  })
                  builder.setPositiveButton("update",DialogInterface.OnClickListener { dialog, wich ->
                      val toDoItem=ToDo(1,editTask.text.toString(),editDate.text.toString())

                      taskList.set(position,toDoItem)
                      recyclerView.adapter!!.notifyItemChanged(position)
                  })
                  builder.show()
              }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCodeVar == requestCode) {

                val newTaskAdded= data?.getParcelableExtra<ToDo>("task") as ToDo
                ToDoRecyclerAdapter(taskList).addTask(newTaskAdded)
            }
        }
    }
}