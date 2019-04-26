package com.dmitrysimakov.kilogram.ui.common.choose_program

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.entity.Program
import com.dmitrysimakov.kilogram.databinding.ItemProgramBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ChooseProgramAdapter(
        appExecutors: AppExecutors,
        clickCallback: ((Program) -> Unit)? = null
) : DataBoundListAdapter<Program, ItemProgramBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<Program>() {
            override fun areItemsTheSame(oldItem: Program, newItem: Program) =
                    oldItem._id == newItem._id
            override fun areContentsTheSame(oldItem: Program, newItem: Program) =
                    oldItem == newItem
        }
) {

    override fun createBinding(parent: ViewGroup) = ItemProgramBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemProgramBinding, item: Program) {
        binding.program = item
    }
}