package com.dmitrysimakov.kilogram.ui.trainings.add_exercise

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.add_exercise.AddExerciseFragment
import com.dmitrysimakov.kilogram.util.EventObserver
import com.dmitrysimakov.kilogram.util.getIntValue

class Trainings_AddExerciseFragment : AddExerciseFragment() {
    
    private val args: Trainings_AddExerciseFragmentArgs by navArgs()
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.start(args.exercise)
        setupNavigation()
    }
    
    override fun addExercise() {
        vm.addExerciseToTraining(args.trainingId, args.num, binding.restET.getIntValue())
    }
    
    private fun setupNavigation() {
        vm.exerciseAddedEvent.observe(viewLifecycleOwner, EventObserver{
            findNavController().popBackStack(R.id.trainingExercisesFragment, false)
        })
    }
}