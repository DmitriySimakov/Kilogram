package com.dmitrysimakov.kilogram.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.data.remote.generateId
import java.util.*

@Entity
data class Photo(
        @PrimaryKey val id: String = generateId(),
        val uri: String = "",
        val dateTime: Date = Date(),
        val lastUpdate: Date = Date(),
        val deleted: Boolean = false
)

class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
}