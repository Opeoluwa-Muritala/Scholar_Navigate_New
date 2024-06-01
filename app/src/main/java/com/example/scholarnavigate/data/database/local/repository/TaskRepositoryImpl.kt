package com.example.scholarnavigate.data.database.local.repository

import com.example.scholarnavigate.data.database.local.data_access_objects.TaskDao
import com.example.scholarnavigate.data.module.Task
import com.example.scholarnavigate.data.database.repositoryInterface.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    val taskDao: TaskDao
): TaskRepository {
    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTaskBySubjectId(courseId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        TODO("Not yet implemented")
    }

    override fun getTasksForSubject(courseId: Int): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun getAllTask(): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

}