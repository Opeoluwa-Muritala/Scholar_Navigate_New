package com.example.scholarnavigate.viewmodels

import android.util.Log
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
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val courseId:Int = savedStateHandle["courseId"] ?: 1

    // Get The Course Id in the View Model
    private val _state = MutableStateFlow(CourseState())
    val state = combine(
        _state,
        taskRepository.getUpcomingTasksForSubject(courseId),
        taskRepository.getCompletedTasksForSubject(courseId),
        sessionRepository.getRecentTenSessionForCourse(courseId),
        sessionRepository.getTotalSessionDurationForCourse(courseId)
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
            CourseEvent.DeleteCourse -> deleteCourse()
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
            is CourseEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(goalStudyHours = event.hours)
                }
            }
            is CourseEvent.UpdateSubject -> updateCourse()
            is CourseEvent.UpdateProgress -> {
                val goalStudyHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f
                _state.update {
                    it.copy(
                        progress = (state.value.studiedHours / goalStudyHours).coerceIn(0f,1f)
                    )
                }
            }
            is CourseEvent.OnTaskIsCompleteChange -> {
                viewModelScope.launch {
                    taskRepository.upsertTask(
                        event.task.copy(isComplete = !event.task.isComplete)
                    )
                }
            }
            is CourseEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(
                        session = event.session
                    )
                }
            }
            CourseEvent.DeleteSession -> deleteSession()
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

    private fun deleteCourse(){
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
                    .getCourseById(courseId = courseId)?.let { course ->
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
                        "Course Fetch Successful",
                    )
                )
            } catch (e: Exception){
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't Update Course ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            } catch (e: NullPointerException){
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't Update Course ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }
    private fun deleteSession() {
        viewModelScope.launch {
            try {
                state.value.session?.let {
                    sessionRepository.deleteSession(it)
                }
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar("Session Deleted Successfully")
                )
            } catch (e: Exception){
                SnackBarEvent.ShowSnackBar(
                    "Couldn't Delete Session. ${e.message}",
                    duration = SnackbarDuration.Long
                )
            }
        }
    }
}