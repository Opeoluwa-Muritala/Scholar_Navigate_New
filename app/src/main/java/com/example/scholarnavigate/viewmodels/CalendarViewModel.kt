package com.example.scholarnavigate.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.scholarnavigate.data.database.repositoryInterface.CourseRepository
import com.example.scholarnavigate.data.database.repositoryInterface.SessionRepository
import com.example.scholarnavigate.data.database.repositoryInterface.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

}