package com.dmitrysimakov.kilogram.ui.profile.subscriptions

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dmitrysimakov.kilogram.ui.profile.followed.FollowedFragment
import com.dmitrysimakov.kilogram.ui.profile.followers.FollowersFragment

const val FOLLOWERS_PAGE = 0
const val FOLLOWED_PAGE = 1

class SubscriptionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
            FOLLOWERS_PAGE to { FollowersFragment() },
            FOLLOWED_PAGE to { FollowedFragment() }
    )
    
    override fun getItemCount() = tabFragmentsCreators.size
    
    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}