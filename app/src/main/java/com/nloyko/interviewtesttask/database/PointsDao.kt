package com.nloyko.interviewtesttask.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for the points table.
 */
@Dao
interface PointsDao {

    /**
     * Observes list of points.
     *
     * @return all points.
     */
    @Query("SELECT * FROM points")
    fun observePoints(): LiveData<List<PointEntity>>

    /**
     * Insert a list of points in the database. If the point already exists, replace it.
     *
     * @param points the list of points to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoints(points: List<PointEntity>)

    /**
     * Delete all points.
     */
    @Query("DELETE FROM points")
    suspend fun deletePoints()
}
