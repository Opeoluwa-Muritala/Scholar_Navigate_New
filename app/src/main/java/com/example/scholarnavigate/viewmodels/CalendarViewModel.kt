package com.example.scholarnavigate.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scholarnavigate.data.database.repositoryInterface.CourseRepository
import com.example.scholarnavigate.data.database.repositoryInterface.SessionRepository
import com.example.scholarnavigate.data.database.repositoryInterface.TaskRepository
import com.example.scholarnavigate.data.module.Task
import com.example.scholarnavigate.viewmodels.events.CalendarEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
): ViewModel() {
    val tasks: StateFlow<List<Task>> = taskRepository.getAllTask()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onEvents(event:CalendarEvents){
        when (event){
            is CalendarEvents.OnTaskIsCompleteChange -> {
                viewModelScope.launch {
                    taskRepository.upsertTask(
                        event.task.copy(isComplete = !event.task.isComplete)
                    )
                }
            }
        }
    }
}