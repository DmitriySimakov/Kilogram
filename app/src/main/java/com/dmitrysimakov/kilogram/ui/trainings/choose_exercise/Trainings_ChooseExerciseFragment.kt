package com.dmitrysimakov.kilogram.ui.trainings.choose_exercise

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment

class Trainings_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val params = Trainings_ChooseExerciseFragmentArgs.fromBundle(arguments!!)
        
        exerciseAdapter.clickCallback = { exercise ->
            wasPopped = true
            findNavController().navigate(Trainings_ChooseExerciseFragmentDirections
                    .toAddExerciseFragment(exercise._id, params.num, params.trainingId))
        }
        
        if (!wasPopped) {
            muscleAdapter.dataWasChanged.observe(this, Observer {
                muscleAdapter.setChecked(params.muscleId.toInt(), true)
            })
        }
    }
}