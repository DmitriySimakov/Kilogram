package com.dmitrysimakov.kilogram.ui.trainings.add_exercise

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.add_exercise.AddExerciseFragment

class Trainings_AddExerciseFragment : AddExerciseFragment() {
    
    private lateinit var params : Trainings_AddExerciseFragmentArgs
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    
        params = Trainings_AddExerciseFragmentArgs.fromBundle(arguments!!)
        viewModel.setExercise(params.exerciseId)
    }
    
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.dialog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.ok -> {
                viewModel.addExercise(params.trainingId)
                viewModel.updateMeasures()
                findNavController().popBackStack(R.id.exercisesFragment, false)
                return true
            }
        }
        return false
    }
}