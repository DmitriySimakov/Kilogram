package com.dmitrysimakov.kilogram.ui.search.people

import android.view.LayoutInflater
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.data.remote.models.SubscriptionAction
import com.dmitrysimakov.kilogram.data.remote.relation.UserWithSubscriptionStatus
import com.dmitrysimakov.kilogram.databinding.ItemUserBinding
import com.dmitrysimakov.kilogram.ui.common.DataBoundListAdapter
import com.dmitrysimakov.kilogram.ui.profile.UserWithSubscriptionStatusDiffCallback

class PeopleListAdapter(
        private val vm: PeopleViewModel,
        clickCallback: ((UserWithSubscriptionStatus) -> Unit)? = null
) : DataBoundListAdapter<UserWithSubscriptionStatus, ItemUserBinding>(
        clickCallback,
        UserWithSubscriptionStatusDiffCallback()
) {
    
    override fun createBinding(parent: ViewGroup): ItemUserBinding = ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
    
    override fun bind(binding: ItemUserBinding, item: UserWithSubscriptionStatus) {
        binding.uwss = item
        binding.subscribeBtn.setOnClickListener { vm.updateSubscriptions(item, SubscriptionAction.SUBSCRIBE) }
        binding.unsubscribeBtn.setOnClickListener { vm.updateSubscriptions(item, SubscriptionAction.UNSUBSCRIBE) }
    }
}