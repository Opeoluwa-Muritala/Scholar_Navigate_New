package com.example.scholarnavigate.data.database.local

import androidx.room.TypeConverter

class colorListConverter {
    @TypeConverter
    fun fromColorList(colorList: List<Int>): String {
        return colorList.joinToString(","){it.toString()}
    }
    @TypeConverter
    fun toColorList(colorListString: String): List<Int> {
        if (colorListString.isBlank()) return emptyList()
        return colorListString.split(",").mapNotNull { it.trim().toIntOrNull() }
    }
}