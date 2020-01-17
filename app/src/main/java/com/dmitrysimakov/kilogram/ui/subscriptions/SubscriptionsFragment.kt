package com.dmitrysimakov.kilogram.ui.subscriptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.subscriptions.SubscriptionsFragmentDirections.Companion.toCreatePostFragment
import com.dmitrysimakov.kilogram.util.navigate
import kotlinx.android.synthetic.main.fragment_subscriptions.*

class SubscriptionsFragment : Fragment() {
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscriptions, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        fab.setOnClickListener { navigate(toCreatePostFragment()) }
    }
}
