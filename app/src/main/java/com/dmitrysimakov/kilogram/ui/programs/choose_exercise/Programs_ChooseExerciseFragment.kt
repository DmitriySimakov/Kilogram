package com.dmitrysimakov.kilogram.ui.programs.choose_exercise

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment

class Programs_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val params = Programs_ChooseExerciseFragmentArgs.fromBundle(arguments!!)
        viewModel.setMuscle(params.muscleId)
        
        adapter.setClickListener { exercise ->
            findNavController().navigate(Programs_ChooseExerciseFragmentDirections
                    .toAddExerciseFragment(exercise._id, params.num, params.programDayId))
        }
    }
}