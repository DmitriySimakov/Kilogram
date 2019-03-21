package com.dmitrysimakov.kilogram.ui.exercises.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dmitrysimakov.kilogram.databinding.FragmentExerciseDetailBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class ExerciseDetailFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    private val viewModel by lazy { getViewModel<ExerciseDetailViewModel>(viewModelFactory) }
    
    private lateinit var binding: FragmentExerciseDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentExerciseDetailBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val params = ExerciseDetailFragmentArgs.fromBundle(arguments!!)
        viewModel.setExercise(params.exerciseId)
        
        activity?.fab?.hide()
    }
}
