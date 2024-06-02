package com.example.scholarnavigate.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scholarnavigate.data.database.repositoryInterface.CourseRepository
import com.example.scholarnavigate.data.database.repositoryInterface.SessionRepository
import com.example.scholarnavigate.data.uimodule.DashboardState
import com.example.scholarnavigate.data.util.toHours
import com.example.scholarnavigate.viewmodels.events.DashBoardEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val sessionRepository: SessionRepository
): ViewModel() {
    private val _state = MutableStateFlow(DashboardState())
    val state = combine(
        _state,
        courseRepository.getTotalCourseCount(),
        courseRepository.getTotalGoalHours(),
        courseRepository.getAllCourses(),
        sessionRepository.getTotalSessionDuration()
    ) { _state, courseCount, goalHours, courses, sessionDuration ->
        _state.copy(
            totalCourseCount = courseCount,
            totalGoalStudyHours = goalHours,
            courses = courses,
            totalStudiedHours = sessionDuration.toHours()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardState()
    )

    fun onEvent(event: DashBoardEvent){
        when (event) {
            DashBoardEvent.DeleteCourse -> TODO()
            DashBoardEvent.SaveCourse -> TODO()
            is DashBoardEvent.onCourseCardColorChange -> TODO()
            is DashBoardEvent.onCourseNameChange -> TODO()
            is DashBoardEvent.onDeleteSessionButtonClick -> TODO()
            is DashBoardEvent.onGoalStudyHoursChange -> TODO()
            is DashBoardEvent.onTaskIsCompleteChange -> TODO()
        }
    }
}