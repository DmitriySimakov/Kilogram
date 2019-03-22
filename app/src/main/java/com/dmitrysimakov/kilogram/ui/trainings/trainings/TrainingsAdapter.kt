package com.dmitrysimakov.kilogram.ui.trainings.trainings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.dmitrysimakov.kilogram.data.relation.TrainingR
import com.dmitrysimakov.kilogram.databinding.ItemTrainingBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class TrainingsAdapter(
        private val sessionTime: LiveData<Int?>,
        private val lifecycleOwner: LifecycleOwner,
        appExecutors: AppExecutors,
        clickCallback: ((TrainingR) -> Unit)?
) : DataBoundListAdapter<TrainingR, ItemTrainingBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ItemTrainingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemTrainingBinding, item: TrainingR) {
        sessionTime.observe(lifecycleOwner, Observer {
            if (it != null) {
                binding.duration = it
            }
        })
        binding.training = item
    }
}