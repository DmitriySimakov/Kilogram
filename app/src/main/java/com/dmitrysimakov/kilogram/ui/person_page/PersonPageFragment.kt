package com.dmitrysimakov.kilogram.ui.person_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.databinding.FragmentPersonPageBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.person_page.PersonPageFragmentDirections.Companion.toMessagesFragment
import com.dmitrysimakov.kilogram.ui.person_page.PersonPageFragmentDirections.Companion.toSubscriptionsTabFragment
import com.dmitrysimakov.kilogram.ui.person_page.subscriptions_tab.SUBSCRIBERS_PAGE
import com.dmitrysimakov.kilogram.ui.person_page.subscriptions_tab.SUBSCRIPTIONS_PAGE
import com.dmitrysimakov.kilogram.util.navigate
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PersonPageFragment : Fragment() {
    
    private val args: PersonPageFragmentArgs by navArgs()
    
    private val vm: PersonPageViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private lateinit var binding: FragmentPersonPageBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPersonPageBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        sharedVM.user.observe(viewLifecycleOwner) { vm.start(it, args.id) }
        
        binding.subscribers.setOnClickListener { navigate(toSubscriptionsTabFragment(SUBSCRIBERS_PAGE, args.id)) }
        binding.subscriptions.setOnClickListener { navigate(toSubscriptionsTabFragment(SUBSCRIPTIONS_PAGE, args.id)) }
        binding.followBtn.setOnClickListener { vm.updateSubscriptions() }
        binding.unfollowBtn.setOnClickListener { vm.updateSubscriptions() }
        binding.writeMessageBtn.setOnClickListener { navigate(toMessagesFragment(args.id)) }
    }
}
