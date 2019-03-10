package com.dmitrysimakov.kilogram.ui.trainings.trainings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.databinding.ItemTrainingBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class TrainingsAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((Training) -> Unit)?
) : DataBoundListAdapter<Training, ItemTrainingBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ItemTrainingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemTrainingBinding, item: Training) {
        binding.training = item
    }
}