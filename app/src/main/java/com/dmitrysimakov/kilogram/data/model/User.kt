package com.dmitrysimakov.kilogram.data.model

import androidx.recyclerview.widget.DiffUtil

data class User(
        val id: String = "",
        val name: String = "",
        val photoUrl: String? = null,
        val gender: String = "",
        val about: String = "",
        val trainingTarget: String = "",
        val gym: String = "",
        val subscribers: List<String> = listOf(),  // ids
        val subscriptions: List<String> = listOf() // ids
)

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}