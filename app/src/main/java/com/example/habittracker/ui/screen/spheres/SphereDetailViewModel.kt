package com.example.habittracker.ui.screen.spheres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habittracker.domain.model.SphereEntity
import com.example.habittracker.domain.model.TaskEntity
import com.example.habittracker.domain.repository.SphereRepository
import com.example.habittracker.domain.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class SphereDetailViewModel(
    private val sphereRepository: SphereRepository,
    private val taskRepository: TaskRepository,
    private val sphereId: Long
) : ViewModel() {

    // Вместо flow { emit(...) } напишите:
    val sphere: StateFlow<SphereEntity?> = sphereRepository.getSphereById(sphereId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val tasks: StateFlow<List<TaskEntity>> = taskRepository.getTasksBySphere(sphereId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    companion object {
        fun provideFactory(
            sphereRepository: SphereRepository,
            taskRepository: TaskRepository,
            sphereId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SphereDetailViewModel(sphereRepository, taskRepository, sphereId) as T
            }
        }
    }
}