package com.example.scholarnavigate.viewmodels

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scholarnavigate.data.database.repositoryInterface.CourseRepository
import com.example.scholarnavigate.data.database.repositoryInterface.SessionRepository
import com.example.scholarnavigate.data.database.repositoryInterface.TaskRepository
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.module.Task
import com.example.scholarnavigate.data.uimodule.DashboardState
import com.example.scholarnavigate.data.util.SnackBarEvent
import com.example.scholarnavigate.data.util.toHours
import com.example.scholarnavigate.viewmodels.events.DashBoardEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository
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

    val tasks: StateFlow<List<Task>> = taskRepository.getAllTask()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val sessions: StateFlow<List<Session>> = sessionRepository.getRecentFiveSessions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    private val _snackbarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvenFlow = _snackbarEventFlow.asSharedFlow()

    fun onEvent(event: DashBoardEvent){
        when (event) {
            is DashBoardEvent.SaveCourse -> saveCourse()
            is DashBoardEvent.onCourseCardColorChange -> {
                _state.update {
                    it.copy(
                        courseCardColors = event.colors
                    )
                }
            }
            is DashBoardEvent.onCourseNameChange -> {
                _state.update {
                    it.copy(
                        courseName = event.name
                    )
                }
            }
            is DashBoardEvent.onDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(
                        session = event.session
                    )
                }
            }
            is DashBoardEvent.onGoalStudyHoursChange -> {
                _state.update {
                    it.copy(
                        goalStudyHours = event.hours
                    )
                }
            }
            is DashBoardEvent.DeleteSession -> TODO()
            is DashBoardEvent.onTaskIsCompleteChange -> {
                viewModelScope.launch {
                    taskRepository.upsertTask(
                        event.task.copy(isComplete = !event.task.isComplete)
                    )
                }
            }
        }
    }

    private fun saveCourse(){
        viewModelScope.launch {
           try {
               courseRepository.upsertCourse(
                   course = Course(
                       name = state.value.courseName,
                       goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                       colors = state.value.courseCardColors.map { it.toArgb() }
                   )
               )
               _state.update {
                   it.copy(
                       courseName = "",
                       goalStudyHours = "",
                       courseCardColors = Course.subjectCardColors.random()
                   )
               }
               _snackbarEventFlow.emit(
                   SnackBarEvent.ShowSnackBar(
                       "Course Saved Successfully",
                   )
               )
           } catch (e:Exception){
               _snackbarEventFlow.emit(
                   SnackBarEvent.ShowSnackBar(
                       "Couldn't Save Course. ${e.message}",
                       duration = SnackbarDuration.Long
                   )
               )
           }

        }
    }
}