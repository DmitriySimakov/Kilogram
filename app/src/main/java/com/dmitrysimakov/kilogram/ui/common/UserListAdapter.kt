package com.dmitrysimakov.kilogram.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.model.UserDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemUserBinding

class UserListAdapter(
        clickCallback: ((User) -> Unit)? = null
) : DataBoundListAdapter<User, ItemUserBinding>(clickCallback, UserDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemUserBinding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemUserBinding, item: User) {
        binding.user = item
    }
}