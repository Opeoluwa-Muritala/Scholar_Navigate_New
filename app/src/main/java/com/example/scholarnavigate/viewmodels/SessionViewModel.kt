package com.example.scholarnavigate.viewmodels

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scholarnavigate.data.database.repositoryInterface.CourseRepository
import com.example.scholarnavigate.data.database.repositoryInterface.SessionRepository
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.uimodule.SessionState
import com.example.scholarnavigate.data.util.SnackBarEvent
import com.example.scholarnavigate.viewmodels.events.SessionEvent
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
class SessionViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val courseRepository: CourseRepository
): ViewModel() {
    private val _state = MutableStateFlow(SessionState())
    val state = combine(
        _state,
        courseRepository.getAllCourses(),
        sessionRepository.getAllSession()
    ){ state , courses, sessions ->
        state.copy(
            courses = courses,
            sessions = sessions
        )

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = SessionState()
    )

    private val _snackbarEventFlow = MutableSharedFlow<SnackBarEvent>()
    val snackBarEvenFlow = _snackbarEventFlow.asSharedFlow()

    fun OnEvent(event: SessionEvent){
        when (event){
            SessionEvent.CheckCourseId -> {}
            SessionEvent.DeleteSession -> deleteSession()
            is SessionEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(
                        session = event.session
                    )
                }
            }
            is SessionEvent.OnRelatedCourseChange -> {
                _state.update {
                    it.copy(
                        relatedSubject = event.course.name,
                        courseId = event.course.courseId
                    )
                }
            }
            is SessionEvent.SaveSession -> insertSession(event.duration)
            is SessionEvent.UpdateCourseIdAndRelatedCourse -> {
                _state.update {
                    it.copy(
                        relatedSubject = event.relatedCourse,
                        courseId = event.courseId
                    )
                }
            }
            SessionEvent.NotifyToUpdateSubject -> notifyToUpdateSubject()
        }
    }

    private fun notifyToUpdateSubject() {
        viewModelScope.launch {
            if (state.value.relatedSubject == null || state.value.courseId == null) {
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar("Please select Subject Related To Course")
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

    private fun insertSession(duration: Long) {

        viewModelScope.launch {
            if (duration < 36){
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "A Single Study Session Cannot Be Less Than 36.seconds",
                    )
                )
                return@launch
            }
            try {
                sessionRepository.insertSession(
                    session = Session(
                        sessionSubjectId = state.value.courseId ?: -1,
                        relatedToSubect = state.value.relatedSubject ?: "",
                        date = Instant.now().toEpochMilli(),
                        duration = duration
                    )
                )
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Session Saved Successfully",
                    )
                )
            } catch (e:Exception){
                _snackbarEventFlow.emit(
                    SnackBarEvent.ShowSnackBar(
                        "Couldn't Save Session. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }

        }
    }
}