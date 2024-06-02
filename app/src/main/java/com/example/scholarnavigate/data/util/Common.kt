package com.example.scholarnavigate.data.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import com.example.scholarnavigate.ui.theme.Green
import com.example.scholarnavigate.ui.theme.Orange
import com.example.scholarnavigate.ui.theme.Red
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class Priority(
    val title: String, val color: Color, val value: Int
){
    LOW("LOW", Green, value = 0),
    MEDIUM("MEDIUM", Orange, value = 1),
    HIGH("HIGH",  Red, value = 2);

    companion object {
        fun fromInt(value: Int) = entries.firstOrNull{it.value == value} ?: MEDIUM
    }
}

fun Long?.changeMillisToDateString(): String{
    val date: LocalDate = this?.let {
        Instant
            .ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } ?: LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}

fun Long.toHours(): Float{
    val hours = this.toFloat() / 3600f
    return "%.2f".format(hours).toFloat()
}