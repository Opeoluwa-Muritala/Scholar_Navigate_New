package com.example.scholarnavigate.data.database.repositoryInterface

import com.example.scholarnavigate.data.module.Course
import kotlinx.coroutines.flow.Flow
import javax.security.auth.Subject

interface CourseRepository {

    suspend fun upsertCourse(course: Course)

    fun getTotalCourseCount() : Flow<Int>

    fun getTotalGoalHours() : Flow<Float>

    suspend fun getCourseById(courseId: Int): Course?

    suspend fun deleteCourse(courseId: Int)

    fun getAllCourses(): Flow<List<Course>>
}