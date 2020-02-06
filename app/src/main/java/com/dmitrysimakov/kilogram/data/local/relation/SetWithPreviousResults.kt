package com.dmitrysimakov.kilogram.data.local.relation

import androidx.recyclerview.widget.DiffUtil

data class SetWithPreviousResults(
        val id: String,
        val weight: Int?,
        val reps: Int?,
        val time: Int?,
        val distance: Int?,
        val prevId: String,
        val prevWeight: Int?,
        val prevReps: Int?,
        val prevTime: Int?,
        val prevDistance: Int?
)

class SetWithPreviousResultsDiffCallback : DiffUtil.ItemCallback<SetWithPreviousResults>() {
    override fun areItemsTheSame(oldItem: SetWithPreviousResults, newItem: SetWithPreviousResults)
            = newItem.id != "" && oldItem.id == newItem.id
            || newItem.prevId != "" && oldItem.prevId == newItem.prevId
    
    override fun areContentsTheSame(oldItem: SetWithPreviousResults, newItem: SetWithPreviousResults)
            = oldItem == newItem
}