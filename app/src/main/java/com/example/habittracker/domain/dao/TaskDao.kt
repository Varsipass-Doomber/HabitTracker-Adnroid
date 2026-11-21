package com.example.habittracker.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habittracker.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query(
        "SELECT * FROM ${TaskEntity.TABLE_NAME} " +
        "WHERE status = '${TaskEntity.STATUS_ACTIVE}' " +
        "ORDER BY deadline ASC"
    )
    fun getActiveTasks(): Flow<List<TaskEntity>>

    @Insert
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query(
        "SELECT * FROM ${TaskEntity.TABLE_NAME} " +
        "WHERE id = :taskId"
    )
    suspend fun getTaskById(taskId: Long): TaskEntity?

//    Must reset progress of repeating tasks at the start of the day
    @Query(
        "UPDATE ${TaskEntity.TABLE_NAME} " +
        "SET current_progress = 0 " +
        "WHERE repeat_type != '${TaskEntity.REPEAT_NONE}'"
    )
    suspend fun resetDailyProgress()

    @Query(
        "SELECT * FROM ${TaskEntity.TABLE_NAME} " +
        "WHERE ${TaskEntity.COL_SPHERE_ID} = :sphereId " +
            "AND status = '${TaskEntity.STATUS_ACTIVE}'"
    )
    fun getTasksBySphere(sphereId: Long): Flow<List<TaskEntity>>
}