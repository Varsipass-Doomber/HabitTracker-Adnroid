package com.example.habittracker.domain.repository

import com.example.habittracker.domain.dao.TaskDao
import com.example.habittracker.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getActiveTasks(): Flow<List<TaskEntity>>
    suspend fun addTask(task: TaskEntity): Long
    suspend fun updateTask(task: TaskEntity)
}

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {
    override fun getActiveTasks() = taskDao.getActiveTasks()
    override suspend fun addTask(task: TaskEntity) = taskDao.insertTask(task)
    override suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)
}