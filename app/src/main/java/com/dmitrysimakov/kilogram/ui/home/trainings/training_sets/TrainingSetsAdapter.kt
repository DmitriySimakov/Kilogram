package com.dmitrysimakov.kilogram.ui.home.trainings.training_sets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.relation.SetWithPreviousResults
import com.dmitrysimakov.kilogram.databinding.ItemTrainingSetBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import com.dmitrysimakov.kilogram.ui.common.DataBoundViewHolder

class TrainingSetsAdapter(
        clickCallback: ((SetWithPreviousResults) -> Unit)
) : DataBoundListAdapter<SetWithPreviousResults, ItemTrainingSetBinding>(
        clickCallback,
        SetWithPreviousResultsDiffCallback()
) {

    override fun createBinding(parent: ViewGroup): ItemTrainingSetBinding = ItemTrainingSetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemTrainingSetBinding>, position: Int) {
        holder.binding.num.text = (position + 1).toString()
        super.onBindViewHolder(holder, position)
    }

    override fun bind(binding: ItemTrainingSetBinding, item: SetWithPreviousResults) {
        binding.set = item
    }
}

private class SetWithPreviousResultsDiffCallback : DiffUtil.ItemCallback<SetWithPreviousResults>() {
    override fun areItemsTheSame(oldItem: SetWithPreviousResults, newItem: SetWithPreviousResults)
            = newItem.id != 0L && oldItem.id == newItem.id
            || newItem.prevId != 0L && oldItem.prevId == newItem.prevId
    
    override fun areContentsTheSame(oldItem: SetWithPreviousResults, newItem: SetWithPreviousResults)
            = oldItem == newItem
}