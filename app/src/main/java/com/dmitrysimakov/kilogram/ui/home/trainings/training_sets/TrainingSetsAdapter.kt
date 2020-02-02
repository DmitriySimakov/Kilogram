package com.dmitrysimakov.kilogram.ui.home.trainings.training_sets

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.local.relation.SetWithPreviousResults
import com.dmitrysimakov.kilogram.data.local.relation.SetWithPreviousResultsDiffCallback
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