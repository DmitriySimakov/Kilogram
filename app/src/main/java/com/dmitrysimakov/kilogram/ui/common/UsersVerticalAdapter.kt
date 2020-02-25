package com.dmitrysimakov.kilogram.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.model.UserDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemUserVerticalBinding

class UsersVerticalAdapter(
        clickCallback: ((User) -> Unit)? = null
) : DataBoundListAdapter<User, ItemUserVerticalBinding>(clickCallback, UserDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemUserVerticalBinding = ItemUserVerticalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemUserVerticalBinding, item: User) {
        binding.user = item
    }
}