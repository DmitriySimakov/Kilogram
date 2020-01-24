package com.dmitrysimakov.kilogram.ui.home.trainings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTraining
import com.dmitrysimakov.kilogram.databinding.ItemTrainingBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class TrainingsAdapter(clickCallback: ((DetailedTraining) -> Unit)?)
    : DataBoundListAdapter<DetailedTraining, ItemTrainingBinding>(
        clickCallback,
        DetailedTrainingDiffCallback()
) {

    override fun createBinding(parent: ViewGroup): ItemTrainingBinding = ItemTrainingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemTrainingBinding, item: DetailedTraining) {
        binding.training = item
    }
}

private class DetailedTrainingDiffCallback : DiffUtil.ItemCallback<DetailedTraining>() {
    override fun areItemsTheSame(oldItem: DetailedTraining, newItem: DetailedTraining) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: DetailedTraining, newItem: DetailedTraining) =
            oldItem == newItem
}