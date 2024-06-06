package com.example.scholarnavigate.viewmodels

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scholarnavigate.data.database.repositoryInterface.CourseRepository
import com.example.scholarnavigate.data.database.repositoryInterface.TaskRepository
import com.example.scholarnavigate.data.module.Task
import com.example.scholarnavigate.data.uimodule.TaskState
import com.example.scholarnavigate.data.util.Priority
import com.example.scholarnavigate.data.util.SnackBarEvent
import com.example.scholarnavigate.viewmodels.events.TaskEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val courseRepository:CourseRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val courseId:Int = savedStateHandle["courseId"] ?: 1
    private val taskId:Int = savedStateHandle["taskId"] ?: 1


    private val _state = MutableStateFlow(TaskState())
    val state = combine(
        _state,
        courseRepository.getAllCourses()
    ) { state, courses ->
        state.copy(
            subjects = courses
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TaskState()
    )

    fun OnEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.OnDateChange -> {
                _state.update {
                    it.copy(
                        dueDate = event.millis
                    )
                }
            }

            is TaskEvent.OnDescriptionChange -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            TaskEvent.OnIsCompleteChange -> {
                _state.update {
                    it.copy(
                        isTaskComplete = !_state.value.isTaskComplete
                    )
                }
            }

            is TaskEvent.OnPriorityChange -> {
                _state.update {
                    it.copy(
                        priority = event.priority
                    )
                }
            }

            is TaskEvent.OnRelatedCourseSelect -> {
                _state.update {
                    it.copy(
                        relatedToSubject = event.course.name,
                        courseId = event.course.courseId
                    )
                }
            }

            is TaskEvent.OnTitleChange -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            TaskEvent.SaveTask -> saveTask()
            TaskEvent.DeleteTask -> TODO()
        }
    }

    private val _snackbarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvenFlow = _snackbarEventFlow.asSharedFlow()


    private fun saveTask() {
        val state = _state.value



        viewModelScope.launch {
            try {
                if (state.relatedToSubject == null || state.courseId == null) {
                    return@launch
                }
                taskRepository.upsertTask(
                    task = Task(
                        title = state.title,
                        description = state.description,
                        dueDates = state.dueDate ?: Instant.now().toEpochMilli(),
                        priority = state.priority.value,
                        relatedCourses = state.relatedToSubject,
                        isComplete = state.isTaskComplete,
                        taskSubjectId = state.courseId,
                        taskId = state.currentTaskId
                    )
                )
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Task Saved Successfully",
                    )
                )
            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't Save Task",
                    )
                )
            }
        }
    }

    private fun fetchTask(){
        viewModelScope.launch{
            try {

                taskRepository
                    .getTaskById(taskId = taskId)?.let { task ->
                        _state.update {
                            it.copy(
                                title = task.title,
                                description = task.description,
                                dueDate = task.dueDates,
                                isTaskComplete = task.isComplete,
                                priority = Priority.fromInt(task.priority),
                                relatedToSubject = task.relatedCourses,
                                currentTaskId = task.taskId,
                                courseId = task.taskSubjectId
                            )
                        }
                    }
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Task Fetch Successful",
                    )
                )
            } catch (e: Exception){
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't Update Course ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }
}