package com.example.scholarnavigate.data.database.local.repository

import com.example.scholarnavigate.data.database.local.data_access_objects.SessionDao
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.database.repositoryInterface.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    val sessionDao: SessionDao
): SessionRepository {
    override suspend fun insertSession(session: Session) {
        return sessionDao.insertSession(session)
    }

    override suspend fun deleteSession(session: Session) {
        TODO("Not yet implemented")
    }

    override fun getAllSession(): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getRecentFiveSessions(): Flow<List<Session>> {
        return sessionDao.getAllSession().take(5)
    }

    override fun getRecentTenSessionForCourse(courseId: Int): Flow<List<Session>> {
        return sessionDao.getRecentSessionForCourse(courseId).take(10)
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return sessionDao.getTotalSessionDuration()
    }

    override fun getTotalSessionDurationForCourse(courseId: Int): Flow<Long> {
        return sessionDao.getTotalSessionDurationForCourse(courseId)
    }

    override fun deleteSessionByCourse(courseId: Int) {
        TODO("Not yet implemented")
    }
}