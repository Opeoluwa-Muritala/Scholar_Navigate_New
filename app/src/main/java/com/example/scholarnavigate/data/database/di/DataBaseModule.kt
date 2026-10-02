package com.example.scholarnavigate.data.database.di

import android.app.Application
import androidx.room.Room
import com.example.scholarnavigate.data.database.local.AppDataBase
import com.example.scholarnavigate.data.database.local.data_access_objects.CourseDao
import com.example.scholarnavigate.data.database.local.data_access_objects.SessionDao
import com.example.scholarnavigate.data.database.local.data_access_objects.TaskDao
import com.example.scholarnavigate.data.database.local.data_access_objects.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): AppDataBase {
        return Room
            .databaseBuilder(
                application,
                AppDataBase::class.java,
                "scholar_navigate.db"
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideCourseDao(dataBase: AppDataBase): CourseDao {
        return dataBase.courseDao()
    }

    @Provides
    @Singleton
    fun provideSessionDao(dataBase: AppDataBase): SessionDao {
        return dataBase.sessionDao()
    }

    @Provides
    @Singleton
    fun provideTaskDao(dataBase: AppDataBase): TaskDao {
        return dataBase.taskDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(dataBase: AppDataBase): UserDao {
        return dataBase.userDao()
    }
}