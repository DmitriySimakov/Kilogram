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
    
    private lateinit var params : Programs_AddExerciseFragmentArgs
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        
        params = Programs_AddExerciseFragmentArgs.fromBundle(arguments!!)
        viewModel.setExercise(params.exerciseId)
    }
    
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.dialog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.ok -> {
                hideKeyboard()
                viewModel.addExerciseToProgramDay(params.programDayId, params.num)
                viewModel.updateMeasures()
                findNavController().popBackStack(R.id.programDayExercisesFragment, false)
                return true
            }
        }
        return false
    }
}