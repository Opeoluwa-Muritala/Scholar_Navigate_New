package com.example.scholarnavigate.data.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.scholarnavigate.data.database.local.data_access_objects.CourseDao
import com.example.scholarnavigate.data.database.local.data_access_objects.SessionDao
import com.example.scholarnavigate.data.database.local.data_access_objects.TaskDao
import com.example.scholarnavigate.data.module.Course
import com.example.scholarnavigate.data.module.Session
import com.example.scholarnavigate.data.module.Task

@Database(
    entities = [Course::class, Session::class, Task::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(colorListConverter::class)
abstract class AppDataBase :RoomDatabase(){
    abstract fun courseDao(): CourseDao
    abstract fun taskDao(): TaskDao
    abstract fun sessionDao(): SessionDao
}