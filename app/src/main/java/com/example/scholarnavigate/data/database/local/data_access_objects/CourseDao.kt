package com.example.scholarnavigate.data.database.local.data_access_objects

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.scholarnavigate.data.module.Course
import kotlinx.coroutines.flow.Flow
import javax.security.auth.Subject

@Dao
interface CourseDao {

    @Upsert
    suspend fun upsertCourse(course: Course)

    @Query("SELECT COUNT(*) FROM COURSE")
    fun getTotalCourseCount() : Flow<Int>

    @Query("SELECT COALESCE(SUM(goalHours), 0) FROM COURSE")
    fun getTotalGoalHours() : Flow<Float>

    @Query("SELECT * FROM COURSE WHERE courseId = :courseId")
    suspend fun getCourseById(courseId: Int): Course?

    @Query("DELETE FROM COURSE WHERE courseId = :courseId")
    suspend fun deleteCourse(courseId: Int)

    @Query("SELECT * FROM COURSE")
    fun getAllCourses(): Flow<List<Course>>

}