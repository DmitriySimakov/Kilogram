package com.dmitrysimakov.kilogram.ui.catalog.exercises.choose_exercise

import android.content.Context
import com.dmitrysimakov.kilogram.ui.catalog.exercises.choose_exercise.Exercises_ChooseExerciseFragmentDirections.Companion.toExerciseDetailFragment
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.util.hideKeyboard
import com.dmitrysimakov.kilogram.util.navigate

class Exercises_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        
        exerciseAdapter.clickCallback = { exercise ->
            hideKeyboard()
            navigate(toExerciseDetailFragment(exercise.name))
        }
    }
}