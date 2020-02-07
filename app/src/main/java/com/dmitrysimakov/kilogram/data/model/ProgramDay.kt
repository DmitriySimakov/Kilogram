package com.dmitrysimakov.kilogram.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.data.remote.generateId
import java.util.*

@Entity(indices = [Index(value = ["programId"])],
        foreignKeys = [
            ForeignKey(
                    entity = Program::class,
                    parentColumns = ["id"],
                    childColumns = ["programId"],
                    onDelete = ForeignKey.CASCADE)
        ]
)
data class ProgramDay(
        val programId: String = "",
        var indexNumber: Int = 1,
        val name: String = "",
        val description: String = "",
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false,
        @PrimaryKey val id: String = generateId()
)

class ProgramDayDiffCallback : DiffUtil.ItemCallback<ProgramDay>() {
    override fun areItemsTheSame(oldItem: ProgramDay, newItem: ProgramDay) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ProgramDay, newItem: ProgramDay) = oldItem == newItem
}