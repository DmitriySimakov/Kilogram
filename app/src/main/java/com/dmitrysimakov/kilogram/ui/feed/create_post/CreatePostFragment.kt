package com.dmitrysimakov.kilogram.ui.feed.create_post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dmitrysimakov.kilogram.databinding.FragmentCreatePostBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.feed.create_post.CreatePostFragmentDirections.Companion.toChooseProgramFragment
import com.dmitrysimakov.kilogram.util.live_data.EventObserver
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setNewValue
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreatePostFragment : Fragment() {
    
    private val vm: CreatePostViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private lateinit var binding: FragmentCreatePostBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreatePostBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        sharedVM.user.observe(viewLifecycleOwner) { vm.user.setNewValue(it) }
        
        sharedVM.program.observe(viewLifecycleOwner, EventObserver { vm.program.setNewValue(it) })
        
        vm.postPublishedEvent.observe(viewLifecycleOwner, EventObserver { popBackStack() })
        
        binding.addProgramButton.setOnClickListener {
            navigate(toChooseProgramFragment())
        }
    }
}
