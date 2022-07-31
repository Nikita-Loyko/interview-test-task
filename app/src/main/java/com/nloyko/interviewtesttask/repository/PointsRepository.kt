package com.nloyko.interviewtesttask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.nloyko.interviewtesttask.database.PointEntity
import com.nloyko.interviewtesttask.database.PointsDao
import com.nloyko.interviewtesttask.network.PointsService
import javax.inject.Inject

class PointsRepository @Inject constructor(
    private val pointsDao: PointsDao,
    private val pointsService: PointsService
) {
    fun observePoints(): LiveData<List<Point>> {
        return pointsDao.observePoints()
            .map { list -> list.sortedBy { it.x }.mapIndexed { i, p -> Point(i + 1, p.x, p.y) } }
    }

    // For the simplicity let's have a result as a simple Boolean value
    suspend fun obtainPoints(count: Int) = try {
        val points = pointsService.obtainPoints(count).points.map { PointEntity(0, it.x, it.y) }
        pointsDao.deletePoints()
        pointsDao.insertPoints(points)
        true
    } catch (e: Exception) {
        false
    }
}