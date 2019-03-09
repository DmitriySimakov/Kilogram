package com.dmitrysimakov.kilogram.ui.trainings.sets

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.relation.ExerciseMeasures
import com.dmitrysimakov.kilogram.databinding.ItemSetBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.DataBoundViewHolder

class TrainingSetsAdapter(
        appExecutors: AppExecutors,
        private val exerciseMeasures: ExerciseMeasures,
        clickCallback: ((TrainingExerciseSet) -> Unit)?
) : DataBoundListAdapter<TrainingExerciseSet, ItemSetBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = ItemSetBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { set?.run { clickCallback?.invoke(this) } }
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemSetBinding>, position: Int) {
        holder.binding.num.text = (position + 1).toString()
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    override fun bind(binding: ItemSetBinding, item: TrainingExerciseSet) {
        binding.set = item
        binding.exerciseMeasures = exerciseMeasures
    }
}