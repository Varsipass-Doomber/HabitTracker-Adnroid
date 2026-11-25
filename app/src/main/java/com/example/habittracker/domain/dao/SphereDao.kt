package com.example.habittracker.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habittracker.domain.model.SphereEntity
import com.example.habittracker.domain.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SphereDao {
    @Query(
        "SELECT * FROM ${SphereEntity.TABLE_NAME} " +
        "ORDER BY level " +
        "DESC, name ASC"
    )
    fun getAllSpheres(): Flow<List<SphereEntity>>

    @Insert
    suspend fun insertSphere(sphere: SphereEntity): Long

    @Update
    suspend fun updateSphere(sphere: SphereEntity)

    @Query(
        "SELECT * FROM ${SphereEntity.TABLE_NAME} " +
        "WHERE id = :sphereId"
    )
    fun getSphereById(sphereId: Long): Flow<SphereEntity?>

    @Query(
        "SELECT SUM(current_exp) " +
        "FROM ${SphereEntity.TABLE_NAME}"
    )
    fun getTotalXp(): Flow<Int?>
}