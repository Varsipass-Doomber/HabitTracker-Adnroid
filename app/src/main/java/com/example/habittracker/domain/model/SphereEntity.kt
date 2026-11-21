package com.example.habittracker.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = SphereEntity.TABLE_NAME)
data class SphereEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val name: String,
    val level: Int = 1,
    val colorHex: String = "#7B5A43",

    @ColumnInfo(name = "current_exp") val currentXp: Int = 0
) {
    companion object {
//        Database constant names
        const val TABLE_NAME = "spheres"
    }
}