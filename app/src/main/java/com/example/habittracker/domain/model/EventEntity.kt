package com.example.habittracker.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(
    tableName = EventEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["id"],
            childColumns = [EventEntity.COL_TASK_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = [EventEntity.COL_TASK_ID])]
)
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    @ColumnInfo(name = COL_TASK_ID) val taskId: Long,
    @ColumnInfo(name = COL_SPHERE_ID) val sphereId: Long,
    @ColumnInfo(name = COL_TIMESTAMP) val timestamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "xp_earned") val xpEarned: Int
) {
    companion object {
//        Database constant names
        const val TABLE_NAME = "events"
        const val COL_TASK_ID = "task_id"
        const val COL_TIMESTAMP = "timestamp"
        const val COL_SPHERE_ID = "sphere_id"
    }
}
