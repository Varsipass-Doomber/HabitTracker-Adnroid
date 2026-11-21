package com.example.habittracker.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.habittracker.domain.model.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert
    suspend fun insertEvent(event: EventEntity)

    @Query(
        "SELECT COALESCE(SUM(xp_earned), 0) FROM ${EventEntity.TABLE_NAME} " +
        "WHERE sphere_id = :sphereId AND timestamp >= :startOfDay"
    )
    suspend fun getTodayXpForSphere(sphereId: Long, startOfDay: Long): Int

    @Query(
        "SELECT COUNT(*) FROM ${EventEntity.TABLE_NAME} " +
        "WHERE ${EventEntity.COL_TASK_ID} = :taskId " +
            "AND timestamp >= :startOfDay"
    )
    suspend fun getTodayCountForTask(taskId: Long, startOfDay: Long): Int

    @Query(
        "SELECT * FROM ${EventEntity.TABLE_NAME} " +
        "ORDER BY timestamp DESC"
    )
    fun getAllEvents(): Flow<List<EventEntity>>


    @Query(
        "SELECT SUM(xp_earned) FROM ${EventEntity.TABLE_NAME}"
    )
    fun getTotalXp(): Flow<Int?>
}