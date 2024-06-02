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
        return courseDao.getTotalCourseCount()
    }

    override fun getTotalGoalHours(): Flow<Float> {
        return courseDao.getTotalGoalHours()
    }

    override suspend fun getCourseById(courseId: Int): Course? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCourse(courseId: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllCourses(): Flow<List<Course>> {
        return courseDao.getAllCourses()
    }
}