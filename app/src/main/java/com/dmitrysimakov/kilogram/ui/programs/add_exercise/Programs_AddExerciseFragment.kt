package com.dmitrysimakov.kilogram.ui.programs.add_exercise

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.add_exercise.AddExerciseFragment
import com.dmitrysimakov.kilogram.util.hideKeyboard

class Programs_AddExerciseFragment : AddExerciseFragment() {
    
    private val params by lazy { Programs_AddExerciseFragmentArgs.fromBundle(arguments!!) }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.setExercise(params.exerciseId)
    }
    
    override fun addExercise() {
        viewModel.addExerciseToProgramDay(params.programDayId, params.num)
    }
}