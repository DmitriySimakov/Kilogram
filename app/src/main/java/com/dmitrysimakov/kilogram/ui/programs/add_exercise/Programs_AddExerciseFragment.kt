package com.dmitrysimakov.kilogram.ui.programs.add_exercise

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.add_exercise.AddExerciseFragment
import com.dmitrysimakov.kilogram.util.getIntValue

class Programs_AddExerciseFragment : AddExerciseFragment() {
    
    private val args: Programs_AddExerciseFragmentArgs by navArgs()
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.setExercise(args.exercise)
    }
    
    override fun addExercise() {
        vm.addExerciseToProgramDay(args.programDayId, args.num, binding.restET.getIntValue())
    }
    
    override fun popBack() {
        findNavController().popBackStack(R.id.programDayExercisesFragment, false)
    }
}