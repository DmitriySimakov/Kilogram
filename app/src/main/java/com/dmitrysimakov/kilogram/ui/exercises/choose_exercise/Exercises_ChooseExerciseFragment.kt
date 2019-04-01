package com.dmitrysimakov.kilogram.ui.exercises.choose_exercise

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment

class Exercises_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
    
        val params = Exercises_ChooseExerciseFragmentArgs.fromBundle(arguments!!)
        viewModel.setMuscle(params.muscleId)
        
        exerciseAdapter.clickCallback = { exercise ->
            findNavController().navigate(Exercises_ChooseExerciseFragmentDirections
                    .toExerciseDetailFragment(exercise._id))
        }
    }
}