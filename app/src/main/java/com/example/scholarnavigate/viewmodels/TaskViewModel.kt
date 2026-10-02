package com.example.scholarnavigate.viewmodels

import androidx.compose.material3.SnackbarDuration
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
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val courseRepository:CourseRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val courseId:Int? = savedStateHandle["courseId"]
    private val taskId:Int? = savedStateHandle["taskId"]


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

    init {
        fetchTask()
        fetchCourse()
    }

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
                        isTaskComplete = !state.value.isTaskComplete
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
            TaskEvent.DeleteTask -> deleteTask()
        }
    }

    private val _snackbarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvenFlow = _snackbarEventFlow.asSharedFlow()

    private fun saveTask() {
        val state = _state.value
        viewModelScope.launch {
            try {
                if (state.relatedToSubject == null || state.courseId == null) {
                    _snackbarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            "Please Fill In All Fields",
                            duration = SnackbarDuration.Long
                        )
                    )
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
                _snackbarEventFlow.emit(SnackBarEvent.NavigateUp)
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
                if (courseId != null && taskId != null) {
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
                            _snackbarEventFlow.emit(
                                SnackBarEvent.ShowSnackBar(
                                    "Task Fetch Successful",
                                )
                            )
                        }
                } else {
                    val state = state.value
                    _state.update {
                        it.copy(
                            title = state.title,
                            description = state.description,
                            dueDate = state.dueDate,
                            isTaskComplete = state.isTaskComplete,
                            priority = state.priority,
                            relatedToSubject = state.relatedToSubject,
                            currentTaskId = state.currentTaskId,
                            courseId = state.courseId
                        )
                    }
                    _snackbarEventFlow.emit(
                        SnackBarEvent.ShowSnackBar(
                            "Task Not Found",
                        )
                    )
                }
            } catch (e: Exception){
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't Update Task ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun fetchCourse(){
        viewModelScope.launch {
            try {
                courseId?.let { id ->
                    courseRepository.getCourseById(id)?.let { course ->
                        _state.update {
                            it.copy(
                                relatedToSubject = course.name,
                                courseId = course.courseId
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't Find Related Subject ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun deleteTask(){
        viewModelScope.launch {
            try {
                val currentTaskId =  state.value.currentTaskId
                if (currentTaskId != null){
                    withContext(Dispatchers.IO){
                        taskRepository.deleteTask(currentTaskId)
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
}