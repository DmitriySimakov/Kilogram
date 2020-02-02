package com.dmitrysimakov.kilogram.ui.profile.subscriptions_tab

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dmitrysimakov.kilogram.ui.profile.subscribers.SubscribersFragment
import com.dmitrysimakov.kilogram.ui.profile.subscriptions.SubscriptionsFragment

const val SUBSCRIBERS_PAGE = 0
const val SUBSCRIPTIONS_PAGE = 1

class SubscriptionsPagerAdapter(fragment: Fragment, userId: String) : FragmentStateAdapter(fragment) {
    
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
            SUBSCRIBERS_PAGE to { SubscribersFragment(userId) },
            SUBSCRIPTIONS_PAGE to { SubscriptionsFragment(userId) }
    )
    
    override fun getItemCount() = tabFragmentsCreators.size
    
    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}