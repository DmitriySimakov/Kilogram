package com.dmitrysimakov.kilogram.ui.common.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.remote.models.Message
import com.dmitrysimakov.kilogram.data.remote.models.MessageDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemMessageBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MessagesListAdapter(
        private val vm: MessagesViewModel
) : DataBoundListAdapter<Message, ItemMessageBinding>(null, MessageDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemMessageBinding = ItemMessageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemMessageBinding, item: Message) {
        binding.vm = vm
        binding.message = item
    }
}