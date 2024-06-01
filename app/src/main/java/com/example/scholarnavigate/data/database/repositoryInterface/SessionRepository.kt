package com.example.scholarnavigate.data.database.repositoryInterface

import com.example.scholarnavigate.data.module.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    suspend fun insertSession(session: Session)

    suspend fun deleteSession(session: Session)

    fun getAllSession(): Flow<List<Session>>

    fun getAllSessionForCourse(courseId: Int): Flow<List<Session>>

    fun getTotalSessionDuration(): Flow<Long>

    fun getTotalSessionDurationForCourse(courseId: Int): Flow<Long>

    fun deleteSessionByCourse(courseId: Int)
}