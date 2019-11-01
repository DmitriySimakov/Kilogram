package com.dmitrysimakov.kilogram.ui.trainings.trainings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.relation.DetailedTraining
import com.dmitrysimakov.kilogram.databinding.ItemTrainingBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class TrainingsAdapter(
        private val sessionTime: LiveData<Int?>,
        private val lifecycleOwner: LifecycleOwner,
        clickCallback: ((DetailedTraining) -> Unit)?
) : DataBoundListAdapter<DetailedTraining, ItemTrainingBinding>(clickCallback,
        object : DiffUtil.ItemCallback<DetailedTraining>() {
            override fun areItemsTheSame(oldItem: DetailedTraining, newItem: DetailedTraining) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: DetailedTraining, newItem: DetailedTraining) =
                    oldItem == newItem
        }
) {

    override fun createBinding(parent: ViewGroup): ItemTrainingBinding = ItemTrainingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemTrainingBinding, item: DetailedTraining) {
        sessionTime.observe(lifecycleOwner, Observer {
            if (it != null) {
                binding.duration = it
            }
        })
        binding.training = item
    }
}