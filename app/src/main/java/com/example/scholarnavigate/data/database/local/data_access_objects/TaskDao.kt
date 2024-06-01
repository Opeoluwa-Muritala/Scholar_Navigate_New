package com.example.scholarnavigate.data.database.local.data_access_objects

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.scholarnavigate.data.module.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Upsert
    suspend fun upsertTask(task: Task)

    @Query("DELETE FROM Task WHERE taskId = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Query("DELETE FROM Task WHERE taskSubjectId = :courseId")
    suspend fun deleteTaskBySubjectId(courseId: Int)

    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    suspend fun getTaskById(taskId: Int) : Task?

    @Query("SELECT * FROM Task WHERE taskSubjectId = :courseId")
    fun getTasksForSubject(courseId: Int): Flow<List<Task>>

    @Query("SELECT * FROM TASK")
    fun getAllTask(): Flow<List<Task>>
}