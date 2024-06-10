package com.example.scholarnavigate.data.database.local.data_access_objects

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import com.example.scholarnavigate.data.module.UserData

@Dao
interface UserDao {
    @Insert
    suspend fun saveUser(user: UserData)

    @Query("DELETE FROM UserData WHERE userId = :userId")
    suspend fun deleteUserData(userId: Int)

    @Query("SELECT * FROM UserData WHERE userId = :userId")
    fun getUserData(userId: Int): UserData?

}