package com.tuwaiq.todolistapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class NewTaskActivity : AppCompatActivity() {
    private lateinit var myTask: EditText
    private lateinit var dateTask: TextView
    private lateinit var addTask: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        myTask= findViewById(R.id.etTask)
        dateTask= findViewById(R.id.tvDate)
        addTask= findViewById(R.id.button_add)
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)

        dateTask.setOnClickListener{
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, month, day ->
                dateTask.setText("$day/${month+1}/$year")
            }, year,month,day).show()
        }


        addTask.setOnClickListener {
            val newTask= ToDo(myTask.text.toString(),dateTask.text.toString())
            val intentTask = Intent()
            if (TextUtils.isEmpty(myTask.text)) {
                setResult(Activity.RESULT_CANCELED, intentTask)
            } else {


                intentTask.putExtra("task",newTask)

                setResult(Activity.RESULT_OK, intentTask)
            }
            finish()

        }




    }
}