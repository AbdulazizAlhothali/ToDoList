package com.tuwaiq.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.recyclerview_item.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fapButton: FloatingActionButton

    private val requestCodeVar = 2
    var taskList = mutableListOf<ToDo>()
    var taskDisplay= mutableListOf<ToDo>()
    lateinit var deletedTask: ToDo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        recyclerView= findViewById(R.id.recyclerview)
        recyclerView.layoutManager=  LinearLayoutManager(this)
        recyclerView.adapter= ToDoRecyclerAdapter(taskList)
        val taskTouchHelper= ItemTouchHelper(simpleCallback)
        taskTouchHelper.attachToRecyclerView(recyclerView)
        taskDisplay.addAll(taskList)
        fapButton= findViewById(R.id.fab)

        fun addItem(){
            val i= Intent(this, NewTaskActivity::class .java)
            startActivityForResult(i, requestCodeVar)
        }
        fapButton.setOnClickListener {
            addItem()

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