package com.example.habittracker.domain.repository

import com.example.habittracker.domain.dao.SphereDao
import com.example.habittracker.domain.model.SphereEntity
import kotlinx.coroutines.flow.Flow

interface SphereRepository {
    fun getAllSpheres(): Flow<List<SphereEntity>>
    suspend fun addSphere(sphere: SphereEntity): Long
}

class SphereRepositoryImpl(private val sphereDao: SphereDao) : SphereRepository {
    override fun getAllSpheres() = sphereDao.getAllSpheres()
    override suspend fun addSphere(sphere: SphereEntity) = sphereDao.insertSphere(sphere)
}