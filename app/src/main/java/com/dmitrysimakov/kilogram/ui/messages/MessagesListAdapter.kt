package com.dmitrysimakov.kilogram.ui.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.FriendlyMessage
import com.dmitrysimakov.kilogram.databinding.ItemMessageBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import com.dmitrysimakov.kilogram.util.AppExecutors

class MessagesListAdapter(
        appExecutors: AppExecutors
) : DataBoundListAdapter<FriendlyMessage, ItemMessageBinding>(appExecutors, null,
        object : DiffUtil.ItemCallback<FriendlyMessage>() {
            override fun areItemsTheSame(oldItem: FriendlyMessage, newItem: FriendlyMessage) =
                    false //TODO
            override fun areContentsTheSame(oldItem: FriendlyMessage, newItem: FriendlyMessage) =
                    oldItem == newItem
        }) {
    
    override fun createBinding(parent: ViewGroup): ItemMessageBinding = ItemMessageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemMessageBinding, item: FriendlyMessage) {
        binding.message = item
    }
}
