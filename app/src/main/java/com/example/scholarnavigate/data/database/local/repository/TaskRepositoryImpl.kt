package com.example.scholarnavigate.data.database.local.repository

import com.example.scholarnavigate.data.database.local.data_access_objects.TaskDao
import com.example.scholarnavigate.data.database.repositoryInterface.TaskRepository
import com.example.scholarnavigate.data.module.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    val taskDao: TaskDao
): TaskRepository {
    override suspend fun upsertTask(task: Task) {
        return taskDao.upsertTask(task)
    }

    override fun getUpcomingTasksForSubject(courseId: Int): Flow<List<Task>> {
        return taskDao.getTasksForSubject(courseId)
            .map { tasks -> tasks.filter { it.isComplete.not() } }
            .map { tasks -> sortTasks(tasks) }
    }


    override fun getCompletedTasksForSubject(courseId: Int): Flow<List<Task>> {
        return taskDao.getTasksForSubject(courseId)
            .map { tasks -> tasks.filter { it.isComplete} }
            .map { tasks -> sortTasks(tasks) }
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    override suspend fun deleteTaskBySubjectId(courseId: Int) {
        taskDao.deleteTaskBySubjectId(courseId)
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }

    override fun getAllTask(): Flow<List<Task>> {
        return taskDao.getAllTask()
            .map { tasks -> tasks.filter { it.isComplete.not() } }
            .map { tasks -> sortTasks(tasks) }
    }

    private fun sortTasks(tasks: List<Task>): List<Task> {
        return tasks.sortedWith(compareBy<Task>{it.dueDates}.thenByDescending { it.priority })
    }
}
