package com.dmitrysimakov.kilogram.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.model.UserDiffCallback
import com.dmitrysimakov.kilogram.databinding.ItemUserMiniBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class UsersHorizontalAdapter(
        clickCallback: ((User) -> Unit)? = null
) : DataBoundListAdapter<User, ItemUserMiniBinding>(clickCallback, UserDiffCallback()) {
    
    override fun createBinding(parent: ViewGroup): ItemUserMiniBinding = ItemUserMiniBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemUserMiniBinding, item: User) {
        binding.user = item
    }
}