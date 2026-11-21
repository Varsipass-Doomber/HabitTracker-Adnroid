package com.example.habittracker.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = TaskEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = SphereEntity::class,
            parentColumns = ["id"],
            childColumns = [TaskEntity.COL_SPHERE_ID],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = [TaskEntity.COL_SPHERE_ID])]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

//    Queries essentials
    @ColumnInfo(name = COL_SPHERE_ID) val sphereId: Long?,
    @ColumnInfo(name = COL_REPEAT_TYPE) val repeatType: String = REPEAT_NONE,
    @ColumnInfo(name = COL_STATUS) val status: String = STATUS_ACTIVE,

//    Other fields
    val title: String,
    val importance: Int,
    val difficulty: Int,
    val deadline: Long? = null,

    @ColumnInfo(name = "repeat_days") val repeatDays: String? = null,
    @ColumnInfo(name = "target_quantity") val targetQuantity: Int = 1,
    @ColumnInfo(name = "current_progress") val currentProgress: Int = 0,
    @ColumnInfo(name = "end_date") val endDate: Long? = null,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
//        Database constant names
        const val TABLE_NAME = "tasks"
        const val COL_SPHERE_ID = "sphere_id"
        const val COL_REPEAT_TYPE = "repeat_type"
        const val COL_STATUS = "status"

//        String const values as "Enums"
        const val REPEAT_NONE = "NONE"
        const val REPEAT_DAILY = "DAILY"
        const val REPEAT_WEEKLY = "WEEKLY"
        const val STATUS_ACTIVE = "ACTIVE"
        const val STATUS_COMPLETED = "COMPLETED"
    }
}