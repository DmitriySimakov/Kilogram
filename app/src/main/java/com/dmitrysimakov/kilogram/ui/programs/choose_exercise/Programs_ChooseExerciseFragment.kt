package com.dmitrysimakov.kilogram.ui.programs.choose_exercise

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment

class Programs_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val params = Programs_ChooseExerciseFragmentArgs.fromBundle(arguments!!)
        
        exerciseAdapter.clickCallback = { exercise ->
            wasPopped = true
            findNavController().navigate(Programs_ChooseExerciseFragmentDirections
                    .toAddExerciseFragment(exercise._id, params.num, params.programDayId))
        }
    
        if (!wasPopped) {
            muscleAdapter.dataWasChanged.observe(this, Observer {
                muscleAdapter.setChecked(params.muscleId.toInt(), true)
            })
        }
    }
    
    
}