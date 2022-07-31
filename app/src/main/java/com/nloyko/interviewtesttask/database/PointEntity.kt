package com.nloyko.interviewtesttask.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Points")
data class PointEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val x: Float,
    val y: Float

)