package com.example.scholarnavigate.data.module

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null,
    val username: String?,
    val profilePictureUrl: String?,
    val email: String?
)
