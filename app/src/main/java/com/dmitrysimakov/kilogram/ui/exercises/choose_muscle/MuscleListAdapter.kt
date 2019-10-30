package com.dmitrysimakov.kilogram.ui.exercises.choose_muscle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.entity.Muscle
import com.dmitrysimakov.kilogram.databinding.ItemChooseMuscleBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MuscleListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((Muscle) -> Unit)? = null
) : DataBoundListAdapter<Muscle, ItemChooseMuscleBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<Muscle>() {
            override fun areItemsTheSame(oldItem: Muscle, newItem: Muscle) =
                    oldItem.name == newItem.name
            override fun areContentsTheSame(oldItem: Muscle, newItem: Muscle) =
                    oldItem == newItem
        }
) {

    override fun createBinding(parent: ViewGroup) = ItemChooseMuscleBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemChooseMuscleBinding, item: Muscle) {
        binding.muscle = item
    }
}