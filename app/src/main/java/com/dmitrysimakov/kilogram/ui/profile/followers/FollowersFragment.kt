package com.dmitrysimakov.kilogram.ui.profile.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.profile.followers.FollowersFragmentDirections.Companion.toMessagesFragment
import com.dmitrysimakov.kilogram.util.navigate
import kotlinx.android.synthetic.main.fragment_people.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowersFragment : Fragment() {
    
    private val vm: FollowersViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private val adapter by lazy { FollowersListAdapter({ navigate(toMessagesFragment(it.id)) }) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        
        sharedVM.user.observe(viewLifecycleOwner) { vm.setUser(it) }
        vm.followers.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}
