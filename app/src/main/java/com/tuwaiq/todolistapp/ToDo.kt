package com.tuwaiq.todolistapp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "task_table")
@Parcelize
data class ToDo (
    var currentDate: String, @PrimaryKey(autoGenerate = true) var id : Int =0 ,var task: String, var desc: String ,var date: String,
    var completed: Boolean):Parcelable{

}


