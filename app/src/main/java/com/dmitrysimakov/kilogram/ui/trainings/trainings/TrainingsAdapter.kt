package com.dmitrysimakov.kilogram.ui.trainings.trainings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.data.relation.TrainingR
import com.dmitrysimakov.kilogram.databinding.ItemTrainingBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class TrainingsAdapter(
        private val sessionTime: LiveData<Int?>,
        private val lifecycleOwner: LifecycleOwner,
        appExecutors: AppExecutors,
        clickCallback: ((TrainingR) -> Unit)?
) : DataBoundListAdapter<TrainingR, ItemTrainingBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<TrainingR>() {
            override fun areItemsTheSame(oldItem: TrainingR, newItem: TrainingR) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: TrainingR, newItem: TrainingR) =
                    oldItem == newItem
        }
) {

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