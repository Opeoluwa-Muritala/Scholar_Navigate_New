package com.example.scholarnavigate.data.module

data class Session(
    val sessionSubjectId: Int,
    val relatedToSubect: String,
    val date: Long,
    val duration: Long,
    val sessionId: Int
)