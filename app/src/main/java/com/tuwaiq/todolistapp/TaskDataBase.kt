package com.tuwaiq.todolistapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
abstract class TaskDataBase: RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object{
        private var INSTSNCE: TaskDataBase? = null
        fun getAppDataBase(context: Context): TaskDataBase?{
            if (INSTSNCE== null){
                INSTSNCE= Room.databaseBuilder(
                    context.applicationContext,
                    TaskDataBase::class.java,
                    "app-database"
                ).build()
            }
            return INSTSNCE
        }
    }

}