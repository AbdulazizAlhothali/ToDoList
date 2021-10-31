package com.tuwaiq.todolistapp

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TaskVM(context: Application) : AndroidViewModel(context) {
    private val repo= TaskRepo(context)

    fun getAllTasks(): MutableLiveData<List<ToDo>> {
        val tasks= MutableLiveData<List<ToDo>>()
        viewModelScope.launch {
            tasks.postValue(repo.getAllTasks())
        }
        return tasks
    }
    fun insertTask(task: ToDo): MutableLiveData<Unit> {
        val int = MutableLiveData<Unit>()
        viewModelScope.launch {

            int.postValue(repo.insertTask(task))
        }
        return int
    }
    fun deleteTask(task: ToDo): MutableLiveData<Unit> {
        val int = MutableLiveData<Unit>()
        viewModelScope.launch {

            int.postValue(repo.deleteTask(task))
        }
        return int
    }
    fun updateTask(task: ToDo): MutableLiveData<Unit> {
        val int = MutableLiveData<Unit>()
        viewModelScope.launch {

            int.postValue(repo.updateTask(task))
        }
        return int
    }
}
