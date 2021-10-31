package com.tuwaiq.todolistapp

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ToDoRecyclerAdapter(private val userList: List<ToDo>, var mainVM: TaskVM) : RecyclerView.Adapter<CustomAdapter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent,false)
        return CustomAdapter(view)
    }

    override fun onBindViewHolder(holder: CustomAdapter, position: Int) {
        val cal = Calendar.getInstance()
        val simpleDateFormat1= SimpleDateFormat("dd/MM/yyyy")
        val localDate= simpleDateFormat1.format(cal.time)


        val task= userList[position]
        holder.currentDate.text= task.currentDate
        holder.task.text= task.task
        holder.date.text= task.date
        holder.infoTask.text= task.desc
        holder.infoTask.isVisible = false
        if (task.completed ){
            holder.checkBox.isChecked=true
            holder.itemView.setBackgroundColor(Color.GRAY)
            holder.task.toggleStrikeThrough(true)
            holder.date.toggleStrikeThrough(true)

        } else if (localDate > task.date && task.date.isNotEmpty()){
            holder.itemView.setBackgroundColor(Color.GRAY)
            holder.task.toggleStrikeThrough(true)
            holder.date.toggleStrikeThrough(true)
            holder.checkBox.isEnabled = false

        }
        else {
            holder.checkBox.isEnabled= true
            holder.checkBox.isChecked=false
            holder.task.toggleStrikeThrough(false)
            holder.date.toggleStrikeThrough(false)
            holder.itemView.setBackgroundColor(Color.WHITE)

        }

        holder.checkBox.setOnClickListener {

            if (holder.checkBox.isChecked) {

                task.completed = true
                holder.itemView.setBackgroundColor(Color.GRAY)
                holder.task.toggleStrikeThrough(true)
                holder.date.toggleStrikeThrough(true)

                mainVM.updateTask(task)

            } else {
                task.completed = false

                holder.itemView.setBackgroundColor(Color.WHITE)
                holder.task.toggleStrikeThrough(false)
                holder.date.toggleStrikeThrough(false)
                mainVM.updateTask(task)

            }
        }


    }

    override fun getItemCount(): Int {
        return userList.size
    }


}

class CustomAdapter (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener  {
    val currentDate= itemView.findViewById<TextView>(R.id.tvCurrentDate)
    val task= itemView.findViewById<TextView>(R.id.task)
    val date= itemView.findViewById<TextView>(R.id.date)
    val checkBox= itemView.findViewById<CheckBox>(R.id.check_box)
    val infoTask= itemView.findViewById<TextView>(R.id.info_tv)
    init {
        itemView.setOnClickListener (this)
    }
    override fun onClick(v: View?) {

        val builder = AlertDialog.Builder(itemView.context)
        builder.setTitle("${task.text}")
        builder.setMessage("${infoTask.text}")
        builder.setCancelable(true)
        builder.setNegativeButton("ok!") { dialog, wich ->
            dialog.cancel()
        }.show()
    }

}


