package com.dmitrysimakov.kilogram.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.databinding.FragmentFeedBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.feed.FeedFragmentDirections.Companion.toCreatePostFragment
import com.dmitrysimakov.kilogram.ui.feed.FeedFragmentDirections.Companion.toPublicProgramDaysFragment
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.setNewValue
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : Fragment() {
    
    private lateinit var binding: FragmentFeedBinding
    
    private val vm: FeedViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private val adapter by lazy { PostsAdapter(sharedVM,
            { navigate(toPublicProgramDaysFragment(it.id)) },
            { vm.like(it) }
    )}
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFeedBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
    
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    
        binding.fab.setOnClickListener { navigate(toCreatePostFragment()) }
        
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        sharedVM.user.observe(viewLifecycleOwner) { vm.user.setNewValue(it) }
        vm.posts.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}
