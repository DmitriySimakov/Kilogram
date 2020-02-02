package com.dmitrysimakov.kilogram.data.local.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Program(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val name: String,
        val description: String = ""
)

class ProgramDiffCallback : DiffUtil.ItemCallback<Program>() {
    override fun areItemsTheSame(oldItem: Program, newItem: Program) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Program, newItem: Program) = oldItem == newItem
}