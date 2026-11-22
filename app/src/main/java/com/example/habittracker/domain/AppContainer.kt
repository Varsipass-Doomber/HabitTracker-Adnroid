package com.example.habittracker.domain

import android.content.Context
import com.example.habittracker.domain.database.AppDatabase
import com.example.habittracker.domain.repository.SphereRepository
import com.example.habittracker.domain.repository.SphereRepositoryImpl
import com.example.habittracker.domain.repository.TaskRepository
import com.example.habittracker.domain.repository.TaskRepositoryImpl

class AppContainer(context: Context) {
    private val database = AppDatabase.getDatabase(context)

//    Repositories
    val sphereRepository: SphereRepository = SphereRepositoryImpl(database.sphereDao())
    val taskRepository: TaskRepository = TaskRepositoryImpl(database.taskDao())
//    TODO: Create EventRepository
}