package com.example.scholarnavigate.data.database.local.data_access_objects

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.scholarnavigate.data.module.UserData
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun saveUser(user: UserData)

    @Query("DELETE FROM UserData WHERE userId = :userId")
    suspend fun deleteUserData(userId: Int)

    @Query("SELECT * FROM UserData")
    suspend fun getUserData(): UserData?
    @Query("SELECT COUNT(*)  FROM UserData")
    fun getUserCount(): Flow<Int>

}