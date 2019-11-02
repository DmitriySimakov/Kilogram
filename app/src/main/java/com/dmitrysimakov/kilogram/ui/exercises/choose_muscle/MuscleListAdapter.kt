package com.dmitrysimakov.kilogram.ui.exercises.choose_muscle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.entity.Muscle
import com.dmitrysimakov.kilogram.databinding.ItemChooseMuscleBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MuscleListAdapter(clickCallback: ((Muscle) -> Unit)? = null)
    : DataBoundListAdapter<Muscle, ItemChooseMuscleBinding>(clickCallback, MuscleDiffCallback()) {

    override fun createBinding(parent: ViewGroup): ItemChooseMuscleBinding = ItemChooseMuscleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemChooseMuscleBinding, item: Muscle) {
        binding.muscle = item
    }
}

private class MuscleDiffCallback : DiffUtil.ItemCallback<Muscle>() {
    override fun areItemsTheSame(oldItem: Muscle, newItem: Muscle) =
            oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: Muscle, newItem: Muscle) =
            oldItem == newItem
}