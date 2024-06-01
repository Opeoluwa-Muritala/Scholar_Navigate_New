package com.example.scholarnavigate.data.database.repositoryInterface

import com.example.scholarnavigate.data.module.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun upsertTask(task: Task)

    suspend fun deleteTask(taskId: Int)

    suspend fun deleteTaskBySubjectId(courseId: Int)

    suspend fun getTaskById(taskId: Int) : Task?

    fun getTasksForSubject(courseId: Int): Flow<List<Task>>

    fun getAllTask(): Flow<List<Task>>
}