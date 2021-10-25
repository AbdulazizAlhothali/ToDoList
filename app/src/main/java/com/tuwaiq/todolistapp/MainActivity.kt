package com.tuwaiq.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.recyclerview_item.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fapButton: FloatingActionButton
    private val requestCodeVar = 2
    private var data = mutableListOf<ToDo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            val user = ToDo(
                0,
            "",
            "")
            data+= user


        recyclerView= findViewById(R.id.recyclerview)
        recyclerView.layoutManager=  LinearLayoutManager(this)
        recyclerView.adapter= ToDoRecyclerAdapter(data)
        fapButton= findViewById(R.id.fab)
        fapButton.setOnClickListener {
            val i= Intent(this, NewTaskActivity::class .java)
            startActivityForResult(i, requestCodeVar)

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCodeVar == requestCode) {
                task.text = data?.getStringExtra("task")

               // task.text = data?.getStringExtra("date")
            }

        }
    }
}