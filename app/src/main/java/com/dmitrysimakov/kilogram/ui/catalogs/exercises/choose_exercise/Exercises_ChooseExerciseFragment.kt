package com.dmitrysimakov.kilogram.ui.catalogs.exercises.choose_exercise

import android.content.Context
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.util.hideKeyboard

class Exercises_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    private val args: Exercises_ChooseExerciseFragmentArgs by navArgs()
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        
        vm.setExerciseTarget(args.target)
        
        exerciseAdapter.clickCallback = { exercise ->
            hideKeyboard()
            findNavController().navigate(Exercises_ChooseExerciseFragmentDirections
                    .toExerciseDetailFragment(exercise.name))
        }
    }
}