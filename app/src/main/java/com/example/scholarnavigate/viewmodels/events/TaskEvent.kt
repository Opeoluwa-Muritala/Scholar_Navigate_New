package com.example.scholarnavigate.viewmodels.events

import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.util.Priority

sealed class TaskEvent {
    data class OnTitleChange(val title:String): TaskEvent()
    data class OnDescriptionChange(val description:String): TaskEvent()
    data class OnDateChange(val millis:Long?): TaskEvent()
    data class OnPriorityChange(val priority: Priority): TaskEvent()
    data class OnRelatedCourseSelect(val course: Course): TaskEvent()
    data object OnIsCompleteChange: TaskEvent()
    data object SaveTask: TaskEvent()

    data object DeleteTask: TaskEvent()

}