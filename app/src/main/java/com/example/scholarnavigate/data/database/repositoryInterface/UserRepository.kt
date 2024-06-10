package com.example.scholarnavigate.data.database.repositoryInterface

import com.example.scholarnavigate.data.module.UserData

interface UserRepository {
    suspend fun saveUserDate(userData: UserData)

    fun getsavedUser(userId: Int): UserData?

    suspend fun deleteUserData(userId:Int)
}