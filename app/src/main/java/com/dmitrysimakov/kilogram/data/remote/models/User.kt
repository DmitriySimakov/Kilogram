package com.dmitrysimakov.kilogram.data.remote.models

import androidx.recyclerview.widget.DiffUtil

data class User(
        val id: String = "",
        val name: String = "",
        val photoUrl: String? = null,
        val followersCount: Int = 0,
        val followedCount: Int = 0
)

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}