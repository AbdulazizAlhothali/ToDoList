package com.tuwaiq.todolistapp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "task_table")
@Parcelize
data class ToDo (@PrimaryKey val task: String, val date: String):Parcelable
