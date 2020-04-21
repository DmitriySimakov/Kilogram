package com.dmitrysimakov.kilogram.ui.common.create_post

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dmitrysimakov.kilogram.databinding.FragmentCreatePostBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.common.create_post.CreatePostFragmentDirections.Companion.toChooseProgramFragment
import com.dmitrysimakov.kilogram.util.dispatchGetImageContentIntent
import com.dmitrysimakov.kilogram.util.live_data.EventObserver
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setNewValue
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val RC_PHOTO_PICKER = 1

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
    
        binding.addImageButton.setOnClickListener { dispatchGetImageContentIntent(RC_PHOTO_PICKER) }
        binding.image.setOnClickListener { dispatchGetImageContentIntent(RC_PHOTO_PICKER) }
        binding.addProgramButton.setOnClickListener { navigate(toChooseProgramFragment()) }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK) {
            vm.imageUri.setNewValue(data?.data)
        }
    }
}
