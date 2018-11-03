package com.dmitrysimakov.kilogram.ui.exercises.chooseMuscle

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.entity.Muscle
import com.dmitrysimakov.kilogram.databinding.ChooseMuscleItemBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.DiffCallback

class ChooseMuscleAdapter(
        appExecutors: AppExecutors,
        private val clickCallback: ((Muscle) -> Unit)
) : DataBoundListAdapter<Muscle, ChooseMuscleItemBinding>(
        appExecutors,
        object : DiffCallback<Muscle>() {}
) {

    override fun createBinding(parent: ViewGroup) = ChooseMuscleItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { muscle?.run { clickCallback.invoke(this) } }
    }

    override fun bind(binding: ChooseMuscleItemBinding, item: Muscle) {
        binding.muscle = item
    }
}