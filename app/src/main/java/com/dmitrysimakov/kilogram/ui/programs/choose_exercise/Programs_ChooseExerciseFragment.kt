package com.dmitrysimakov.kilogram.ui.programs.choose_exercise

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.util.hideKeyboard

class Programs_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val args: Programs_ChooseExerciseFragmentArgs by navArgs()
        vm.setProgramDay(args.programDayId)
        
        exerciseAdapter.clickCallback = { exercise ->
            hideKeyboard()
            findNavController().navigate(Programs_ChooseExerciseFragmentDirections
                    .toAddExerciseFragment(exercise.name, args.num, args.programDayId))
        }
    }
    
    
}