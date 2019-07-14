package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.databinding.ItemProgramDayBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ChooseProgramDayAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((ProgramDay) -> Unit)? = null
) : DataBoundListAdapter<ProgramDay, ItemProgramDayBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<ProgramDay>() {
            override fun areItemsTheSame(oldItem: ProgramDay, newItem: ProgramDay) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: ProgramDay, newItem: ProgramDay) =
                    oldItem == newItem
        }
) {
 
    
    override fun createBinding(parent: ViewGroup) = ItemProgramDayBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramDayBinding, item: ProgramDay) {
        binding.day = item
    }
}