package com.example.scholarnavigate.data.module

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null,
    val username: String?  = null,
    val profilePictureUrl: String?  = null,
    val email: String?  = null
)
