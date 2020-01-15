package com.dmitrysimakov.kilogram.ui.profile.followers

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.databinding.ItemFollowerBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import com.dmitrysimakov.kilogram.ui.profile.UserDiffCallback

class FollowersListAdapter(
        private val sendMessageClickCallback: ((User) -> Unit),
        clickCallback: ((User) -> Unit)? = null
) : DataBoundListAdapter<User, ItemFollowerBinding>(
        clickCallback,
        UserDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemFollowerBinding = ItemFollowerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemFollowerBinding, item: User) {
        binding.user = item
        binding.sendMessageBtn.setOnClickListener { sendMessageClickCallback.invoke(item) }
    }
}