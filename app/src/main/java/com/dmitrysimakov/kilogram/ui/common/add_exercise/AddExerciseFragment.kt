package com.dmitrysimakov.kilogram.ui.common.add_exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dmitrysimakov.kilogram.databinding.FragmentAddExerciseBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

abstract class AddExerciseFragment: DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var binding: FragmentAddExerciseBinding
    
    protected lateinit var viewModel: AddExerciseViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddExerciseBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        viewModel = getViewModel(viewModelFactory)
        binding.viewModel = viewModel
        
        activity?.fab?.hide()
    }
}