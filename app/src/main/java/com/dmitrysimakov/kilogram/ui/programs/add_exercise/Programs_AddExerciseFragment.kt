package com.dmitrysimakov.kilogram.ui.programs.add_exercise

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.add_exercise.AddExerciseFragment
import com.dmitrysimakov.kilogram.util.getIntValue

class Programs_AddExerciseFragment : AddExerciseFragment() {
    
    private val params by lazy { Programs_AddExerciseFragmentArgs.fromBundle(arguments!!) }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.setExercise(params.exercise)
    }
    
    override fun addExercise() {
        vm.addExerciseToProgramDay(params.programDayId, params.num, binding.restET.getIntValue())
    }
    
    override fun popBack() {
        findNavController().popBackStack(R.id.programDayExercisesFragment, false)
    }
}