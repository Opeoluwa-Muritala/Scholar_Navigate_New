package com.example.scholarnavigate.data.database.local.repository

import com.example.scholarnavigate.data.database.local.data_access_objects.SessionDao
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.database.repositoryInterface.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    val sessionDao: SessionDao
): SessionRepository {
    override suspend fun insertSession(session: Session) {
        return sessionDao.insertSession(session)
    }

    override suspend fun deleteSession(session: Session) {
        sessionDao.deleteSession(session)
    }

    override fun getAllSession(): Flow<List<Session>> {
        return sessionDao.getAllSession()
            .map { session-> session.sortedByDescending { it.date } }
    }

    override fun getRecentFiveSessions(): Flow<List<Session>> {
        return sessionDao.getAllSession()
            .map { sessions -> sessions.sortedByDescending { it.date }.take(5) }
    }

    override fun getRecentTenSessionForCourse(courseId: Int): Flow<List<Session>> {
        return sessionDao.getRecentSessionForCourse(courseId)
            .map { sessions -> sessions.sortedByDescending { it.date }.take(10) }
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return sessionDao.getTotalSessionDuration()
    }

    override fun getTotalSessionDurationForCourse(courseId: Int): Flow<Long> {
        return sessionDao.getTotalSessionDurationForCourse(courseId)
    }

    override suspend fun deleteSessionByCourse(courseId: Int) {
        sessionDao.deleteSessionByCourse(courseId)
    }
}