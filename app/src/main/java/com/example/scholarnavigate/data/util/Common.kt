package com.example.scholarnavigate.data.util

import android.icu.text.CaseMap.Title
import androidx.compose.ui.graphics.Color
import com.example.scholarnavigate.ui.theme.Green
import com.example.scholarnavigate.ui.theme.Orange
import com.example.scholarnavigate.ui.theme.Red

enum class Priority(
    val title: String, val color: Color, val value: Int
){
    LOW("LOW", Green, value = 0),
    MEDIUM("MEDIUM", Orange, value = 1),
    HIGH("HIGH",  Red, value = 2);

    companion object {
        fun fromInt(value: Int) = values().firstOrNull{it.value == value} ?: MEDIUM
    }
}