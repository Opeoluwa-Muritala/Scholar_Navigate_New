package com.example.scholarnavigate.data.database.local.data_access_objects

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.scholarnavigate.data.module.Session
import kotlinx.coroutines.flow.Flow

interface SessionDao {
    @Insert
    suspend fun insertSession(session: Session)

    @Delete
    suspend fun deleteSession(session: Session)

    @Query("SELECT * FROM SESSION")
    fun getAllSession(): Flow<List<Session>>

    @Query("SELECT * FROM SESSION WHERE sessionSubjectId = :courseId")
    fun getAllSessionForCourse(courseId: Int): Flow<List<Session>>

    @Query("SELECT SUM(duration) FROM SESSION ")
    fun getTotalSessionDuration(): Flow<Long>

    @Query("SELECT SUM(duration) FROM SESSION WHERE sessionSubjectId = :courseId")
    fun getTotalSessionDurationForCourse(courseId: Int): Flow<Long>

    @Query("DELETE FROM SESSION WHERE sessionSubjectId = :courseId")
    fun deleteSessionByCourse(courseId: Int)
}