package com.dmitrysimakov.kilogram.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dmitrysimakov.kilogram.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_catalog_tab.*

class CatalogTabFragment : Fragment() {
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_catalog_tab, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        viewPager.adapter = CatalogPagerAdapter(this)
    
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }
    
    private fun getTabTitle(position: Int): String? {
        return when (position) {
            EXERCISES_PAGE -> getString(R.string.exercises)
            PROGRAMS_PAGE -> getString(R.string.training_programs)
            else -> null
        }
    }
}
