package com.nloyko.interviewtesttask.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The Room Database that contains the Task table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [PointEntity::class], version = 1, exportSchema = false)
abstract class PointsDatabase : RoomDatabase() {
    abstract fun pointsDao(): PointsDao
}
