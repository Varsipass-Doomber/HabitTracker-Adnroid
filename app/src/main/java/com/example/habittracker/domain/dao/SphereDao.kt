package com.example.habittracker.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habittracker.domain.model.SphereEntity
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
    suspend fun getSphereById(sphereId: Long): SphereEntity?

    @Query(
        "SELECT SUM(current_exp) " +
        "FROM ${SphereEntity.TABLE_NAME}"
    )
    fun getTotalXp(): Flow<Int?>
}