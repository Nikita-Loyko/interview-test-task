package com.nloyko.interviewtesttask.displaypoints

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.nloyko.interviewtesttask.R
import com.nloyko.interviewtesttask.repository.Point


/**
 * [BindingAdapter]s for the [Point]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Point>?) {
    items?.let {
        listView.adapter = PointsAdapter(items)
    }
}

@BindingAdapter("app:items")
fun setItems(chart: LineChart, items: List<Point>?) {
    items?.let {
        val dataSet = LineDataSet(
            items.map { p -> Entry(p.x, p.y) },
            chart.resources.getString(R.string.points_label)
        ).apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            color = ContextCompat.getColor(chart.context, R.color.purple_500)
            valueTextSize = 10f
            lineWidth = 1f
            fillColor = ContextCompat.getColor(chart.context, R.color.white)
            setCircleColor(ContextCompat.getColor(chart.context, R.color.purple_700))
            circleHoleColor = ContextCompat.getColor(chart.context, R.color.purple_200)
        }
        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.description = Description().apply { text = "" }
        chart.invalidate()
    }
}
