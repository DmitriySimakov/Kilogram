package com.dmitrysimakov.kilogram.ui.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.remote.Chat
import com.dmitrysimakov.kilogram.databinding.ItemChatBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.AppExecutors

class ChatsListAdapter(
        appExecutors: AppExecutors,
        private val userId: String,
        clickCallback: ((Chat) -> Unit)? = null
) : DataBoundListAdapter<Chat, ItemChatBinding>(appExecutors, clickCallback,
        object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat) =
                    oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Chat, newItem: Chat) =
                    oldItem == newItem
        }) {
    
    override fun createBinding(parent: ViewGroup): ItemChatBinding = ItemChatBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemChatBinding, item: Chat) {
        binding.chat = item
        binding.currentUserId = userId
    }
}
