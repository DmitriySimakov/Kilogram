package com.dmitrysimakov.kilogram.ui.common.person_page.subscriptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.UsersVerticalAdapter
import com.dmitrysimakov.kilogram.ui.common.person_page.subscriptions_tab.SubscriptionsTabFragmentDirections.Companion.toPersonPageFragment
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.android.synthetic.main.fragment_people.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscriptionsFragment(private val userId: String) : Fragment() {
    
    private val vm: SubscriptionsViewModel by viewModel()
    
    private val adapter by lazy { UsersVerticalAdapter { navigate(toPersonPageFragment(it.id)) } }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_subscribers, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        
        vm.userId.setNewValue(userId)
        vm.subscriptions.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}
