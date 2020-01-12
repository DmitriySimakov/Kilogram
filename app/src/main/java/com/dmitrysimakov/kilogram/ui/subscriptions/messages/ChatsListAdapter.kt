package com.dmitrysimakov.kilogram.ui.subscriptions.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.remote.Chat
import com.dmitrysimakov.kilogram.databinding.ItemChatBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ChatsListAdapter(
        private val vm: ChatsViewModel,
        clickCallback: ((Chat) -> Unit)? = null
) : DataBoundListAdapter<Chat, ItemChatBinding>(
        clickCallback,
        ChatDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemChatBinding = ItemChatBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemChatBinding, item: Chat) {
        binding.vm = vm
        binding.chat = item
    }
}

private class ChatDiffCallback : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Chat, newItem: Chat) =
            oldItem == newItem
}