package com.dmitrysimakov.kilogram.data.relation

import androidx.recyclerview.widget.DiffUtil
import java.util.*

data class DetailedTraining(
        val id: String,
        val startDateTime: Date,
        var duration: Int? = null,
        val programName: String? = null,
        val programDayName: String? = null
)

class DetailedTrainingDiffCallback : DiffUtil.ItemCallback<DetailedTraining>() {
    override fun areItemsTheSame(oldItem: DetailedTraining, newItem: DetailedTraining) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: DetailedTraining, newItem: DetailedTraining) =
            oldItem == newItem
}