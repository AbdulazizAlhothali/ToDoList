package com.tuwaiq.todolistapp

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ClipDescription
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class NewTaskActivity : AppCompatActivity() {
    private lateinit var currentDate: TextView
    private lateinit var myTask: EditText
    lateinit var description: EditText
    private lateinit var dateTask: TextView
    private lateinit var addTask: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        currentDate= findViewById(R.id.current_time)
        myTask= findViewById(R.id.etTask)
        description = findViewById(R.id.et_desc)
        dateTask= findViewById(R.id.tvEditDate)
        addTask= findViewById(R.id.button_add)
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val year = cal.get(Calendar.YEAR)

        dateTask.setOnClickListener{
            DatePickerDialog(this, {
                    view, year, month, day ->
                dateTask.setText("$day/${month+1}/$year")
            }, year,month,day).show()
        }


        val simpleDateFormat= SimpleDateFormat("dd/MM/yyyy")
        currentDate.text= simpleDateFormat.format(cal.getTime())


        addTask.setOnClickListener {

            val newTask= ToDo(currentDate.text.toString(),0,myTask.text.toString(),description.text.toString(),dateTask.text.toString(),false)
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