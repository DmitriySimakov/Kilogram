package com.dmitrysimakov.kilogram.ui.exercises.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dmitrysimakov.kilogram.databinding.FragmentExerciseDetailBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ExerciseDetailFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentExerciseDetailBinding

    private lateinit var viewModel: ExerciseDetailViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentExerciseDetailBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ExerciseDetailViewModel::class.java)

        val params = ExerciseDetailFragmentArgs.fromBundle(arguments)
        viewModel.setExercise(params.exerciseId.toLong())

        viewModel.exercise.observe(this, Observer {
            binding.exercise = it
        })
    }
}
