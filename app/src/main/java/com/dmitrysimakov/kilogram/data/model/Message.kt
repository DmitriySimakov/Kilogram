package com.dmitrysimakov.kilogram.data.model

import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.remote.generateId
import java.util.*

data class Message(
        val senderId: String = "",
        val text: String? = null,
        val imageUrl: String? = null,
        val timestamp: Date = Date(),
        val wasRead: Boolean = false,
        val id: String = generateId()
)

class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
}