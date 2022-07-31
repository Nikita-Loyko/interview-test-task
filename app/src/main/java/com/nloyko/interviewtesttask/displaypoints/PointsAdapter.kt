package com.nloyko.interviewtesttask.displaypoints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nloyko.interviewtesttask.databinding.PointsListItemBinding
import com.nloyko.interviewtesttask.repository.Point

class PointsAdapter(private val points: List<Point>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(points[position])
    }

    override fun getItemCount() = points.size

    class ItemViewHolder(private val binding: PointsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(point: Point) {
            binding.point = point
        }

        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val binding = PointsListItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)

                return ItemViewHolder(binding)
            }
        }
    }
}