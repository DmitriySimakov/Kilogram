package com.dmitrysimakov.kilogram.ui.trainings.choose_muscle

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.ChooseMuscleFragment

class Trainings_ChooseMuscleFragment : ChooseMuscleFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val params = Trainings_ChooseMuscleFragmentArgs.fromBundle(arguments!!)
        
        adapter.clickCallback = { muscle ->
            findNavController().navigate(Trainings_ChooseMuscleFragmentDirections
                    .toChooseExerciseFragment(muscle._id, params.num, params.trainingId))
        }
    }
}