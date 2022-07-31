package com.nloyko.interviewtesttask.network

import com.google.gson.annotations.SerializedName

data class PointsResponse(
    @SerializedName("points") val points: List<Point>
) {
    data class Point(
        @SerializedName("x") val x: Float,
        @SerializedName("y") val y: Float,
    )
}
