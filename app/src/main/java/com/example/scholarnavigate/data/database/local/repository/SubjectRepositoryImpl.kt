package com.example.scholarnavigate.data.database.local.repository

import com.example.scholarnavigate.data.database.local.data_access_objects.CourseDao
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.database.repositoryInterface.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.security.auth.Subject

class CourseRepositoryImpl @Inject constructor(
    private val courseDao: CourseDao
): CourseRepository {
    override suspend fun upsertCourse(course: Course) {
        courseDao.upsertCourse(course)
    }

    override fun getTotalCourseCount(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun getTotalGoalHours(): Flow<Float> {
        TODO("Not yet implemented")
    }

    override suspend fun getCourseById(courseId: Int): Subject? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCourse(courseId: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllCourses(): Flow<List<Subject>> {
        TODO("Not yet implemented")
    }
}