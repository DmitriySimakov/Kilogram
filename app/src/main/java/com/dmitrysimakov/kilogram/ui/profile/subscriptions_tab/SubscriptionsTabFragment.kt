package com.dmitrysimakov.kilogram.ui.profile.subscriptions_tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.tab_fragment.*

class SubscriptionsTabFragment : Fragment() {
    
    private val args: SubscriptionsTabFragmentArgs by navArgs()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tab_fragment, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val adapter = SubscriptionsPagerAdapter(this, args.userId)
        viewPager.adapter = adapter
        
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    
        viewPager.currentItem = args.pageId
    }
    
    private fun getTabTitle(position: Int): String? {
        return when (position) {
            SUBSCRIBERS_PAGE -> getString(R.string.subscribers)
            SUBSCRIPTIONS_PAGE -> getString(R.string.subscriptions)
            else -> null
        }
    }
}
