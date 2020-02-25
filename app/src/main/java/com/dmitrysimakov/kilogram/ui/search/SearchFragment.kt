package com.dmitrysimakov.kilogram.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dmitrysimakov.kilogram.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    
    private lateinit var binding: FragmentSearchBinding
    
    private val peopleAdapter by lazy { UsersHorizontalAdapter() }
    
    private val vm: SearchViewModel by viewModel()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        binding.peoplesRV.adapter = peopleAdapter
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        vm.people.observe(viewLifecycleOwner) { peopleAdapter.submitList(it) }
    }
}
