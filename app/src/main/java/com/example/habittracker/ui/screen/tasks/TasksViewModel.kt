package com.example.habittracker.ui.screen.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habittracker.domain.model.SphereEntity
import com.example.habittracker.domain.model.TaskEntity
import com.example.habittracker.domain.repository.SphereRepository
import com.example.habittracker.domain.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

data class TaskWithSphere(
    val task: TaskEntity,
    val sphere: SphereEntity?
)

data class TasksUiState(
    val overdue: List<TaskWithSphere> = emptyList(),
    val today: List<TaskWithSphere> = emptyList(),
    val soon: List<TaskWithSphere> = emptyList()
)


class TasksViewModel(
    taskRepository: TaskRepository,
    sphereRepository: SphereRepository
): ViewModel() {
//    Creating combined flow to map spheres and tasks with each other
    val uiState: StateFlow<TasksUiState> = combine(
        taskRepository.getActiveTasks(),
        sphereRepository.getAllSpheres()
    ) { tasks, spheres ->
        val tasksWithSpheres = tasks.map { task ->
            TaskWithSphere(task, spheres.find { it.id == task.sphereId })
        }

//        Time in millis
        val startOfToday = getStartOfDay()
        val startOfTomorrow = startOfToday + 24 * 60 * 60 * 1000

        val overdue = mutableListOf<TaskWithSphere>()
        val today = mutableListOf<TaskWithSphere>()
        val soon = mutableListOf<TaskWithSphere>()

        tasksWithSpheres.forEach { tws ->
            val deadline = tws.task.deadline
            when {
                deadline != null && deadline < startOfToday -> overdue.add(tws)
                deadline != null && deadline in startOfToday until startOfTomorrow -> today.add(tws)
                deadline != null -> soon.add(tws)
                else -> today.add(tws)
            }
        }

        TasksUiState(overdue, today, soon)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TasksUiState()
    )

    private fun getStartOfDay(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    companion object {
        fun provideFactory(
            taskRepository: TaskRepository,
            sphereRepository: SphereRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TasksViewModel(taskRepository, sphereRepository) as T
            }
        }
    }
}