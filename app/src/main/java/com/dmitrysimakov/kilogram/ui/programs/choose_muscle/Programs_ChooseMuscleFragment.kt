package com.dmitrysimakov.kilogram.ui.programs.choose_muscle

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.ChooseMuscleFragment

class Programs_ChooseMuscleFragment : ChooseMuscleFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val params = Programs_ChooseMuscleFragmentArgs.fromBundle(arguments!!)
        
        adapter.clickCallback = { muscle ->
            findNavController().navigate(Programs_ChooseMuscleFragmentDirections
                    .toChooseExerciseFragment(muscle._id, params.num, params.programDayId))
        }
    }
}