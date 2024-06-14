package com.example.scholarnavigate.data.module

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.scholarnavigate.ui.theme.gradient1
import com.example.scholarnavigate.ui.theme.gradient2
import com.example.scholarnavigate.ui.theme.gradient3
import com.example.scholarnavigate.ui.theme.gradient4
import com.example.scholarnavigate.ui.theme.gradient5

@Entity
data class Course(
    val name: String,
    val goalHours: Float,
    val colors: List<Int>,
    val syllabus: String,
    @PrimaryKey
    val courseId: Int? = null
){
    companion object {
        val subjectCardColors = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }

}
