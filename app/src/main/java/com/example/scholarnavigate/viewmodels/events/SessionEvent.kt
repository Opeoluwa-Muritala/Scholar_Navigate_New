package com.example.scholarnavigate.viewmodels.events

import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.module.Session
import java.time.Duration

sealed class SessionEvent {
    data class OnRelatedCourseChange(val course: Course): SessionEvent()
    data class SaveSession(val duration: Long): SessionEvent()
    data class OnDeleteSessionButtonClick(val session: Session): SessionEvent()
    data object DeleteSession: SessionEvent()
    data object CheckCourseId: SessionEvent()
    data object NotifyToUpdateSubject: SessionEvent()
    data class UpdateCourseIdAndRelatedCourse(
        val courseId: Int?,
        val relatedCourse: String?
    ): SessionEvent()
}