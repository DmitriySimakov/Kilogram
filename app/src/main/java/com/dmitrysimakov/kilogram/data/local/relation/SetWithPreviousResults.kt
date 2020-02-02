package com.dmitrysimakov.kilogram.data.local.relation

import androidx.recyclerview.widget.DiffUtil

data class SetWithPreviousResults(
        val id: Long,
        val weight: Int?,
        val reps: Int?,
        val time: Int?,
        val distance: Int?,
        val prevId: Long,
        val prevWeight: Int?,
        val prevReps: Int?,
        val prevTime: Int?,
        val prevDistance: Int?
)

class SetWithPreviousResultsDiffCallback : DiffUtil.ItemCallback<SetWithPreviousResults>() {
    override fun areItemsTheSame(oldItem: SetWithPreviousResults, newItem: SetWithPreviousResults)
            = newItem.id != 0L && oldItem.id == newItem.id
            || newItem.prevId != 0L && oldItem.prevId == newItem.prevId
    
    override fun areContentsTheSame(oldItem: SetWithPreviousResults, newItem: SetWithPreviousResults)
            = oldItem == newItem
}