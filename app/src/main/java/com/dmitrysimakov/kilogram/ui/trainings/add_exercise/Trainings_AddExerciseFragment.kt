package com.dmitrysimakov.kilogram.ui.trainings.add_exercise

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.add_exercise.AddExerciseFragment
import com.dmitrysimakov.kilogram.util.getIntValue

class Trainings_AddExerciseFragment : AddExerciseFragment() {
    
    private val params by lazy { Trainings_AddExerciseFragmentArgs.fromBundle(arguments!!) }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.setExercise(params.exerciseId)
    }
    
    override fun addExercise() {
        viewModel.addExerciseToTraining(params.trainingId, params.num, binding.restET.getIntValue())
    }
    
    override fun popBack() {
        findNavController().popBackStack(R.id.trainingExercisesFragment, false)
    }
}