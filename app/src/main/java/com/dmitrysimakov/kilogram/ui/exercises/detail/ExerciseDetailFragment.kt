package com.dmitrysimakov.kilogram.ui.exercises.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dmitrysimakov.kilogram.databinding.FragmentExerciseDetailBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ExerciseDetailFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewModel: ExerciseDetailViewModel = getViewModel(viewModelFactory)
        val args = ExerciseDetailFragmentArgs.fromBundle(arguments!!)
        viewModel.setExercise(args.exerciseId)

        FragmentExerciseDetailBinding.inflate(inflater).apply {
            vm = viewModel
            setLifecycleOwner(this@ExerciseDetailFragment)
            return root
        }
    }
}
