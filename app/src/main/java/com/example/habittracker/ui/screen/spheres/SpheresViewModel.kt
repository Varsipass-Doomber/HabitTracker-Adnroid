package com.example.habittracker.ui.screen.spheres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habittracker.domain.model.SphereEntity
import com.example.habittracker.domain.repository.SphereRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SpheresViewModel(private val repository: SphereRepository) : ViewModel() {

    val spheres: StateFlow<List<SphereEntity>> = repository.getAllSpheres()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addSphere(name: String, color: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.addSphere(SphereEntity(name = name, colorHex = color))
        }
    }

    companion object {
        fun provideFactory(repository: SphereRepository): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SpheresViewModel(repository) as T
            }
        }
    }
}