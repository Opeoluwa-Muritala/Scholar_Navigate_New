package com.example.scholarnavigate.data.database.local.repository

import com.example.scholarnavigate.data.database.local.data_access_objects.UserDao
import com.example.scholarnavigate.data.database.repositoryInterface.UserRepository
import com.example.scholarnavigate.data.module.UserData
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
): UserRepository
{
    override suspend fun saveUserDate(userData: UserData) {
        userDao.saveUser(userData)
    }

    override fun getsavedUser(userId: Int): UserData? {
        return userDao.getUserData(userId)
    }

    override suspend fun deleteUserData(userId: Int) {
        userDao.deleteUserData(userId)
    }

}