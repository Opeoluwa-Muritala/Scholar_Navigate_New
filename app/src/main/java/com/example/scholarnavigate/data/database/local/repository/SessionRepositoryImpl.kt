package com.example.scholarnavigate.data.database.local.repository

import com.example.scholarnavigate.data.database.local.data_access_objects.SessionDao
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.database.repositoryInterface.SessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    val sessionDao: SessionDao
): SessionRepository {
    override suspend fun insertSession(session: Session) {
        sessionDao.insertSession(session)
    }

    override suspend fun deleteSession(session: Session) {
        TODO("Not yet implemented")
    }

    override fun getAllSession(): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getAllSessionForCourse(courseId: Int): Flow<List<Session>> {
        TODO("Not yet implemented")
    }

    override fun getTotalSessionDuration(): Flow<Long> {
        return sessionDao.getTotalSessionDuration()
    }

    override fun getTotalSessionDurationForCourse(courseId: Int): Flow<Long> {
        TODO("Not yet implemented")
    }

    override fun deleteSessionByCourse(courseId: Int) {
        TODO("Not yet implemented")
    }
}