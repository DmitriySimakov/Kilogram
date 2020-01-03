package com.dmitrysimakov.kilogram.ui.subscriptions.people

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.databinding.ItemUserBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class PeopleListAdapter(
        private val sendMessageClickCallback: ((User) -> Unit),
        clickCallback: ((User) -> Unit)? = null
) : DataBoundListAdapter<User, ItemUserBinding>(
        clickCallback,
        UserDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemUserBinding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemUserBinding, item: User) {
        binding.user = item
        binding.sendMessageBtn.setOnClickListener { sendMessageClickCallback.invoke(item) }
    }
}

private class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem == newItem
}