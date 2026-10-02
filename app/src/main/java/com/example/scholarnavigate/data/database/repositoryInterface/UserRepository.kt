package com.example.scholarnavigate.data.database.repositoryInterface

import com.example.scholarnavigate.data.module.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUserData(userData: UserData)

    suspend fun getsavedUser(): UserData?

    fun getUserCount(): Flow<Int>
    suspend fun deleteUserData(userId:Int)
}