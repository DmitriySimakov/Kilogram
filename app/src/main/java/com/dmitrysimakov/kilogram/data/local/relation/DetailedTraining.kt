package com.dmitrysimakov.kilogram.data.local.relation

import androidx.recyclerview.widget.DiffUtil
import org.threeten.bp.OffsetDateTime

data class DetailedTraining(
        val id: Long,
        val startDateTime: OffsetDateTime,
        var duration: Int? = null,
        var programDayId: Long? = null,
        val programDayName: String? = null,
        val programName: String? = null
)

class DetailedTrainingDiffCallback : DiffUtil.ItemCallback<DetailedTraining>() {
    override fun areItemsTheSame(oldItem: DetailedTraining, newItem: DetailedTraining) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: DetailedTraining, newItem: DetailedTraining) =
            oldItem == newItem
}