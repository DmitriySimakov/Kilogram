package com.dmitrysimakov.kilogram.ui.common.choose_muscle

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.entity.Muscle
import com.dmitrysimakov.kilogram.databinding.ItemChooseMuscleBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class MuscleListAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((Muscle) -> Unit)? = null
) : DataBoundListAdapter<Muscle, ItemChooseMuscleBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ItemChooseMuscleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { muscle?.run { clickCallback?.invoke(this) } }
    }

    override fun bind(binding: ItemChooseMuscleBinding, item: Muscle) {
        binding.muscle = item
    }
}