package com.dmitrysimakov.kilogram.ui.subscriptions.people

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.databinding.ItemUserBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import com.dmitrysimakov.kilogram.ui.profile.UserDiffCallback

class PeopleListAdapter(
        private val followClickCallback: ((User) -> Unit),
        clickCallback: ((User) -> Unit)? = null
) : DataBoundListAdapter<User, ItemUserBinding>(
        clickCallback,
        UserDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemUserBinding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemUserBinding, item: User) {
        binding.user = item
        binding.followBtn.setOnClickListener { followClickCallback.invoke(item) }
    }
}