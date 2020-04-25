package com.dmitrysimakov.kilogram.ui.messages.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.Message
import com.dmitrysimakov.kilogram.data.model.MessageDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemMessageBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MessagesAdapter(
        private val vm: MessagesViewModel
) : DataBoundListAdapter<Message, ItemMessageBinding>(null, MessageDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemMessageBinding = ItemMessageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemMessageBinding, item: Message) {
        binding.vm = vm
        binding.message = item
    }
}