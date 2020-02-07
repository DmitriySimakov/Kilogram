package com.dmitrysimakov.kilogram.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.data.remote.generateId
import java.util.*

@Entity
data class Program(
        val name: String = "",
        val description: String = "",
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false,
        @PrimaryKey val id: String = generateId()
)

class ProgramDiffCallback : DiffUtil.ItemCallback<Program>() {
    override fun areItemsTheSame(oldItem: Program, newItem: Program) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Program, newItem: Program) = oldItem == newItem
}