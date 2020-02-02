package com.dmitrysimakov.kilogram.ui.home.trainings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTraining
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTrainingDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemTrainingBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class TrainingsAdapter(
        clickCallback: ((DetailedTraining) -> Unit)?
) : DataBoundListAdapter<DetailedTraining, ItemTrainingBinding>(
        clickCallback,
        DetailedTrainingDiffCallback()
) {

    override fun createBinding(parent: ViewGroup): ItemTrainingBinding = ItemTrainingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemTrainingBinding, item: DetailedTraining) {
        binding.training = item
    }
}