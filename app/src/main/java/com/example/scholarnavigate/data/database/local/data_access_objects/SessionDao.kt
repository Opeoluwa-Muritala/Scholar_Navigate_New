package com.example.scholarnavigate.data.database.local.data_access_objects

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.scholarnavigate.data.module.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Insert
    suspend fun insertSession(session: Session)

    @Delete
    suspend fun deleteSession(session: Session)

    @Query("SELECT * FROM SESSION")
    fun getAllSession(): Flow<List<Session>>

    @Query("SELECT * FROM SESSION WHERE sessionSubjectId = :courseId")
    fun getRecentSessionForCourse(courseId: Int): Flow<List<Session>>

    @Query("SELECT COALESCE(SUM(duration), 0) FROM SESSION ")
    fun getTotalSessionDuration(): Flow<Long>

    @Query("SELECT COALESCE(SUM(duration), 0) FROM SESSION WHERE sessionSubjectId = :courseId")
    fun getTotalSessionDurationForCourse(courseId: Int): Flow<Long>

    @Query("DELETE FROM SESSION WHERE sessionSubjectId = :courseId")
    suspend fun deleteSessionByCourse(courseId: Int)
}