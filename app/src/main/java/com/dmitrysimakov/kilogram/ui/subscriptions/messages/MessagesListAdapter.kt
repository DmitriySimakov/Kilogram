package com.dmitrysimakov.kilogram.ui.subscriptions.messages

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.remote.Message
import com.dmitrysimakov.kilogram.databinding.ItemMessageBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class MessagesListAdapter(
        private val vm: MessagesViewModel
) : DataBoundListAdapter<Message, ItemMessageBinding>(
        null,
        MessageDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemMessageBinding = ItemMessageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemMessageBinding, item: Message) {
        binding.vm = vm
        binding.message = item
        
        val isUserMessage = item.senderId == vm.user.value?.id
        binding.messageBox.layoutParams = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            gravity = if (isUserMessage) Gravity.END else Gravity.START
        }
    }
}

private class MessageDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Message, newItem: Message) =
            oldItem == newItem
}