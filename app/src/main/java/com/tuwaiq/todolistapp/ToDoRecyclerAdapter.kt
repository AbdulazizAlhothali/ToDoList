package com.tuwaiq.todolistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoRecyclerAdapter(private val userList: List<ToDo>) : RecyclerView.Adapter<CustomAdapter>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent,false)
        return CustomAdapter(view)
    }

    override fun onBindViewHolder(holder: CustomAdapter, position: Int) {
        val user= userList[position]
        holder.task.text= "${user.task} \n ${user.date} "
        //holder.date.text= user.date.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}

class CustomAdapter (itemView: View) : RecyclerView.ViewHolder(itemView)  {
    val task= itemView.findViewById<TextView>(R.id.task)
    //val date= itemView.findViewById<TextView>(R.id.date)


}
