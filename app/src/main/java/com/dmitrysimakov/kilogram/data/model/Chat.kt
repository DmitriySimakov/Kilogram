package com.dmitrysimakov.kilogram.data.model

import androidx.recyclerview.widget.DiffUtil

data class Chat(
        val id: String = "",
        val companion: User = User(),
        val lastMessage: Message = Message()
)

class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Chat, newItem: Chat) = oldItem == newItem
}