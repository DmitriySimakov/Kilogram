package com.dmitrysimakov.kilogram.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.model.Post
import com.dmitrysimakov.kilogram.databinding.ItemPostBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class PostsListAdapter(
        private val vm: FeedViewModel,
        clickCallback: ((Post) -> Unit)? = null
) : DataBoundListAdapter<Post, ItemPostBinding>(clickCallback, PostsDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemPostBinding = ItemPostBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemPostBinding, item: Post) {
        binding.vm = vm
        binding.post = item
    }
}

private class PostsDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}