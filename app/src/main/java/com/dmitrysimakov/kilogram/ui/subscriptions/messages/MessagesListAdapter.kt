package com.dmitrysimakov.kilogram.ui.subscriptions.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.remote.Message
import com.dmitrysimakov.kilogram.databinding.ItemMessageBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MessagesListAdapter : DataBoundListAdapter<Message, ItemMessageBinding>(
        null,
        MessageDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemMessageBinding = ItemMessageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemMessageBinding, item: Message) {
        binding.message = item
    }
}

private class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Message, newItem: Message) =
            oldItem == newItem
}