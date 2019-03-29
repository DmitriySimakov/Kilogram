package com.dmitrysimakov.kilogram.ui.exercises.choose_exercise

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment

class Exercises_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        
        exerciseAdapter.clickCallback = { exercise ->
            wasPopped = true
            findNavController().navigate(Exercises_ChooseExerciseFragmentDirections
                    .toExerciseDetailFragment(exercise._id))
        }
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val params = Exercises_ChooseExerciseFragmentArgs.fromBundle(arguments!!)
    
        if (!wasPopped) {
            muscleAdapter.dataWasChanged.observe(this, Observer {
                muscleAdapter.setChecked(params.muscleId.toInt(), true)
            })
        }
    }
}