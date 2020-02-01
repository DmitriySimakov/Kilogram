package com.dmitrysimakov.kilogram.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.databinding.ItemFollowerBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class SubscriptionsListAdapter(
        clickCallback: ((User) -> Unit)? = null
) : DataBoundListAdapter<User, ItemFollowerBinding>(clickCallback, UserDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemFollowerBinding = ItemFollowerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemFollowerBinding, item: User) {
        binding.user = item
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}