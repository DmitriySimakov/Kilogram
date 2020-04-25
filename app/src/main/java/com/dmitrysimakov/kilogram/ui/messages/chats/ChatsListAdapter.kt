package com.dmitrysimakov.kilogram.ui.messages.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.Chat
import com.dmitrysimakov.kilogram.data.model.ChatDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemChatBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class ChatsListAdapter(
        private val vm: ChatsViewModel,
        clickCallback: ((Chat) -> Unit)? = null
) : DataBoundListAdapter<Chat, ItemChatBinding>(clickCallback, ChatDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemChatBinding = ItemChatBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemChatBinding, item: Chat) {
        binding.vm = vm
        binding.chat = item
    }
}