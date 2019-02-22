package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import com.dmitrysimakov.kilogram.databinding.TrainingDayItemBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.DataBoundListAdapter

class ChooseProgramDayAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((ProgramDay) -> Unit)? = null
) : DataBoundListAdapter<ProgramDay, TrainingDayItemBinding>(appExecutors, clickCallback) {

    override fun createBinding(parent: ViewGroup) = TrainingDayItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false).apply {
        root.setOnClickListener { day?.run { clickCallback?.invoke(this) } }
    }

    override fun bind(binding: TrainingDayItemBinding, item: ProgramDay) {
        binding.day = item
    }
}