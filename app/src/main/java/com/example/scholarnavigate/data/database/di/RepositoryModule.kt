package com.example.scholarnavigate.data.database.di

import com.example.scholarnavigate.data.database.local.repository.CourseRepositoryImpl
import com.example.scholarnavigate.data.database.local.repository.SessionRepositoryImpl
import com.example.scholarnavigate.data.database.local.repository.TaskRepositoryImpl
import com.example.scholarnavigate.data.database.repositoryInterface.CourseRepository
import com.example.scholarnavigate.data.database.repositoryInterface.SessionRepository
import com.example.scholarnavigate.data.database.repositoryInterface.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCourseRepository(
        impl: CourseRepositoryImpl
    ): CourseRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl: SessionRepositoryImpl
    ): SessionRepository
}