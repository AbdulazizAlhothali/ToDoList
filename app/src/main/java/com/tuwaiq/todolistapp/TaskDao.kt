package com.tuwaiq.todolistapp

import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    fun insertTask (task: ToDo)

    @Query ("SELECT * From task_table")
    fun getAllTasks(): List<ToDo>

    @Delete
    fun deleteTask (task: ToDo)

    @Update
    fun updateTask (task: ToDo)
}