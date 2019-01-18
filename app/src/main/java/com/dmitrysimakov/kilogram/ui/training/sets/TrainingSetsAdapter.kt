package com.dmitrysimakov.kilogram.ui.training.sets

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.databinding.SetItemBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.DataBoundViewHolder
import com.dmitrysimakov.kilogram.util.IdDiffCallback

class TrainingSetsAdapter(
        appExecutors: AppExecutors,
        private val clickCallback: ((TrainingExerciseSet) -> Unit)
) : DataBoundListAdapter<TrainingExerciseSet, SetItemBinding>(appExecutors, IdDiffCallback()) {

    override fun createBinding(parent: ViewGroup) = SetItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { set?.run { clickCallback.invoke(this) } }
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<SetItemBinding>, position: Int) {
        holder.binding.num.text = (position + 1).toString()
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    override fun bind(binding: SetItemBinding, item: TrainingExerciseSet) {
        binding.set = item
    }
}