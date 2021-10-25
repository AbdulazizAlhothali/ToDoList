package com.tuwaiq.todolistapp

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ToDo ( val id: Int, val task: String, val date:String): Parcelable
