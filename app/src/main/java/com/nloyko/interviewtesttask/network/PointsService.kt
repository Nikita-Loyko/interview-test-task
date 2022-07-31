package com.nloyko.interviewtesttask.network

import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://hr-challenge.interactivestandard.com"

interface PointsService {
    @GET("/api/test/points")
    suspend fun obtainPoints(@Query("count") count: Int): PointsResponse
}