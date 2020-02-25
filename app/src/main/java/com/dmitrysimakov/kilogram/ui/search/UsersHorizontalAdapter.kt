package com.dmitrysimakov.kilogram.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.model.UserDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemUserHorizontalBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class UsersHorizontalAdapter(
        clickCallback: ((User) -> Unit)? = null
) : DataBoundListAdapter<User, ItemUserHorizontalBinding>(clickCallback, UserDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemUserHorizontalBinding = ItemUserHorizontalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemUserHorizontalBinding, item: User) {
        binding.user = item
    }
}