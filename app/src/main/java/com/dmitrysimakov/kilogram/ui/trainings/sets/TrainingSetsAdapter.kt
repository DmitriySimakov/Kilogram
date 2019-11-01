package com.dmitrysimakov.kilogram.ui.trainings.sets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.relation.ExerciseMeasures
import com.dmitrysimakov.kilogram.data.relation.SetWithPreviousResults
import com.dmitrysimakov.kilogram.databinding.ItemSetBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class TrainingSetsAdapter(
        private val exerciseMeasures: ExerciseMeasures,
        clickCallback: ((SetWithPreviousResults) -> Unit)
) : DataBoundListAdapter<SetWithPreviousResults, ItemSetBinding>(clickCallback,
        object : DiffUtil.ItemCallback<SetWithPreviousResults>() {
            override fun areItemsTheSame(oldItem: SetWithPreviousResults, newItem: SetWithPreviousResults)
                    = newItem._id != 0L && oldItem._id == newItem._id
                    || newItem.prev_id != 0L && oldItem.prev_id == newItem.prev_id
    
            override fun areContentsTheSame(oldItem: SetWithPreviousResults, newItem: SetWithPreviousResults)
                    = oldItem == newItem
        }
) {

    override fun createBinding(parent: ViewGroup): ItemSetBinding = ItemSetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemSetBinding>, position: Int) {
        holder.binding.num.text = (position + 1).toString()
        super.onBindViewHolder(holder, position)
    }

    override fun bind(binding: ItemSetBinding, item: SetWithPreviousResults) {
        binding.set = item
        binding.exerciseMeasures = exerciseMeasures
    }
}