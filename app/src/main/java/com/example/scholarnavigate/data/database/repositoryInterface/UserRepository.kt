package com.example.scholarnavigate.data.database.repositoryInterface

import com.example.scholarnavigate.data.module.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUserDate(userData: UserData)

    fun getsavedUser(): UserData?

    fun getUserCount(): Flow<Int>
    suspend fun deleteUserData(userId:Int)
}