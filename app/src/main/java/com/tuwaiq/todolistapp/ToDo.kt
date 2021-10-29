package com.tuwaiq.todolistapp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "task_table")
@Parcelize
data class ToDo (@PrimaryKey (autoGenerate = true) val id: Int = 1,val task: String, val date: String, var completed: Boolean = false):Parcelable


