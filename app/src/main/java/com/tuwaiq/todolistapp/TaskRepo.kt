package com.tuwaiq.todolistapp

import android.content.Context
import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepo(context: Context) {
    private val taskDB = TaskDataBase.getAppDataBase(context)!!

    suspend fun getAllTasks(): List<ToDo> = withContext(Dispatchers.IO) {
        taskDB.taskDao.getAllTasks()
    }

    suspend fun insertTask(task: ToDo) = withContext(Dispatchers.IO) {


        taskDB.taskDao.insertTask(task)
    }

    suspend fun deleteTask(task: ToDo) = withContext(Dispatchers.IO){
        taskDB.taskDao.deleteTask(task)
    }

    suspend fun updateTask(task: ToDo) = withContext(Dispatchers.IO){
        taskDB.taskDao.updateTask(task)
    }

}