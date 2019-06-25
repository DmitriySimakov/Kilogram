package com.dmitrysimakov.kilogram.ui.exercises.choose_exercise

import android.content.Context
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.util.hideKeyboard

class Exercises_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
    
        val params = Exercises_ChooseExerciseFragmentArgs.fromBundle(arguments!!)
        viewModel.setMuscle(params.muscleId)
        
        exerciseAdapter.clickCallback = { exercise ->
            hideKeyboard()
            findNavController().navigate(Exercises_ChooseExerciseFragmentDirections
                    .toExerciseDetailFragment(exercise._id))
        }
    }
}