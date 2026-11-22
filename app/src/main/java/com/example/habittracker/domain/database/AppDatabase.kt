package com.example.habittracker.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habittracker.domain.dao.EventDao
import com.example.habittracker.domain.dao.SphereDao
import com.example.habittracker.domain.dao.TaskDao
import com.example.habittracker.domain.model.EventEntity
import com.example.habittracker.domain.model.SphereEntity
import com.example.habittracker.domain.model.TaskEntity

@Database(
    entities = [
        SphereEntity::class,
        TaskEntity::class,
        EventEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun sphereDao(): SphereDao
    abstract fun taskDao(): TaskDao
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        const val DB_NAME = "habit_tracker_db"

        fun getDatabase(context: Context): AppDatabase {
            return  INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}