package com.example.scholarnavigate.data.database.local.repository

import com.example.scholarnavigate.data.database.local.data_access_objects.UserDao
import com.example.scholarnavigate.data.database.repositoryInterface.UserRepository
import com.example.scholarnavigate.data.module.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao,
): UserRepository
{
    override suspend fun saveUserData(userData: UserData) {
        userDao.saveUser(userData)
    }

    override fun getsavedUser(): UserData? {
        return userDao.getUserData()
    }

    override fun getUserCount(): Flow<Int> {
        return userDao.getUserCount()
    }

    override suspend fun deleteUserData(userId: Int) {
        userDao.deleteUserData(userId)
    }

}