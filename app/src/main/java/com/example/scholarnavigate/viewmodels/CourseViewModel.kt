package com.example.scholarnavigate.viewmodels

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.example.scholarnavigate.data.database.repositoryInterface.CourseRepository
import com.example.scholarnavigate.data.database.repositoryInterface.SessionRepository
import com.example.scholarnavigate.data.database.repositoryInterface.TaskRepository
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.uimodule.CourseState
import com.example.scholarnavigate.data.util.SnackBarEvent
import com.example.scholarnavigate.data.util.toHours
import com.example.scholarnavigate.viewmodels.events.CourseEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    val courseId = savedStateHandle["courseId"] ?: 0

    // Get The Course Id in the View Model

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

    init {
        fetchSubject()
    }

    fun onEvent(event: CourseEvent){
        when(event){
            CourseEvent.DeleteCourse -> DeleteCourse()
            CourseEvent.DeleteSession -> TODO()
            is CourseEvent.OnCourseCardColorChange -> {
                _state.update {
                    it.copy(courseCardColors = event.colors)
                }
            }
            is CourseEvent.OnCourseCardNameChange -> {
                _state.update {
                    it.copy(courseName = event.name)
                }
            }
            is CourseEvent.OnDeleteSessionButtonClick -> TODO()
            is CourseEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(goalStudyHours = event.hours)
                }
            }
            is CourseEvent.OnTaskIsCompleteChange -> TODO()
            is CourseEvent.UpdateSubject -> updateCourse()
            is CourseEvent.UpdateProgress -> {
                val goalStudyHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f
                _state.update {
                    it.copy(
                        progress = (state.value.studiedHours / goalStudyHours).coerceIn(0f,1f)
                    )
                }
            }
        }
    }

    private val _snackbarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvenFlow = _snackbarEventFlow.asSharedFlow()
    private fun updateCourse(){
        viewModelScope.launch {
            courseRepository.upsertCourse(
                course = Course(
                    name = state.value.courseName,
                    goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                    colors = state.value.courseCardColors.map { it.toArgb() },
                    courseId = state.value.currentCourseId
                )
            )
        }
    }

    private fun DeleteCourse(){
        viewModelScope.launch {
            try {
                val currentCourseId =  state.value.currentCourseId
                if (currentCourseId != null){
                    withContext(Dispatchers.IO){
                        courseRepository.deleteCourse(currentCourseId)
                    }

                    _snackbarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar("Course Deleted Successfully")
                    )
                    _snackbarEventFlow.emit(
                        SnackBarEvent.NavigateUp
                    )
                } else {
                    _snackbarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(" No Course To Delete")
                    )
                    _snackbarEventFlow.emit(
                        SnackBarEvent.NavigateUp
                    )
                }


            } catch (e: Exception){
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar("Couldn't Delete Course", duration =  SnackbarDuration.Long)
                )
            }
        }
    }

    private fun fetchSubject(){
        viewModelScope.launch {
            try {
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
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Course Updated Successfully",
                    )
                )
            } catch (e: Exception){
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't Update Course",
                    )
                )
            }
            }

    }
}