package com.dmitrysimakov.kilogram.ui.profile

import androidx.recyclerview.widget.DiffUtil
import com.dmitrysimakov.kilogram.data.remote.relation.UserWithSubscriptionStatus

class UserWithSubscriptionStatusDiffCallback : DiffUtil.ItemCallback<UserWithSubscriptionStatus>() {
    override fun areItemsTheSame(oldItem: UserWithSubscriptionStatus, newItem: UserWithSubscriptionStatus) =
            oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: UserWithSubscriptionStatus, newItem: UserWithSubscriptionStatus) =
            oldItem == newItem
}