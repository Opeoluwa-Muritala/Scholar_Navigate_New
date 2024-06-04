package com.example.scholarnavigate.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.example.scholarnavigate.data.database.repositoryInterface.CourseRepository
import com.example.scholarnavigate.data.database.repositoryInterface.SessionRepository
import com.example.scholarnavigate.data.database.repositoryInterface.TaskRepository
import com.example.scholarnavigate.data.uimodule.CourseState
import com.example.scholarnavigate.data.util.toHours
import com.example.scholarnavigate.viewmodels.events.CourseEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository,
): ViewModel() {
    val courseId = 0

    init {
        fetchSubject()
    }

    private val _state = MutableStateFlow(CourseState())
    val state = combine(
        _state,
        taskRepository.getUpcomingTasksForSubject(0),
        taskRepository.getCompletedTasksForSubject(0),
        sessionRepository.getRecentTenSessionForCourse(0),
        sessionRepository.getTotalSessionDurationForCourse(0)
    ){ state, upcomingTasks, completedTasks, recentSessions, totalSessionDuration ->
        state.copy(
            upcomingTask = upcomingTasks,
            completedTask = completedTasks,
            recentSession = recentSessions,
            studiedHours = totalSessionDuration.toHours()
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CourseState()
    )

    fun onEvent(event: CourseEvent){
        when(event){
            CourseEvent.DeleteCourse -> TODO()
            CourseEvent.DeleteSession -> TODO()
            is CourseEvent.OnCourseCardColorChange -> TODO()
            is CourseEvent.OnCourseCardNameChange -> TODO()
            is CourseEvent.OnDeleteSessionButtonClick -> TODO()
            is CourseEvent.OnGoalStudyHoursChange -> TODO()
            is CourseEvent.OnTaskIsCompleteChange -> TODO()
            CourseEvent.UpdateSubject -> TODO()
        }
    }

    private fun fetchSubject(){
        viewModelScope.launch {
            courseRepository
                .getCourseById(0)?.let { course ->
                    _state.update {
                        it.copy(
                            courseName = course.name,
                            goalStudyHours = course.goalHours.toString(),
                            courseCardColors = course.colors.map { Color(it) },
                            currentCourseId = course.courseId
                        )
                    }
                }
        }
    }
}