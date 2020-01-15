package com.dmitrysimakov.kilogram.ui.profile

import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.remote.User

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}