package com.dmitrysimakov.kilogram.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.remote.relation.UserWithSubscriptionStatus
import com.dmitrysimakov.kilogram.databinding.ItemFollowerBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter

class SubscriptionsListAdapter(
        private val sendMessageClickCallback: ((UserWithSubscriptionStatus) -> Unit),
        clickCallback: ((UserWithSubscriptionStatus) -> Unit)? = null
) : DataBoundListAdapter<UserWithSubscriptionStatus, ItemFollowerBinding>(
        clickCallback,
        UserWithSubscriptionStatusDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemFollowerBinding = ItemFollowerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemFollowerBinding, item: UserWithSubscriptionStatus) {
        binding.uwss = item
        binding.sendMessageBtn.setOnClickListener { sendMessageClickCallback.invoke(item) }
    }
}