package com.dmitrysimakov.kilogram.ui.diaries.trainings.choose_exercise

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.util.EventObserver
import com.dmitrysimakov.kilogram.util.setXNavIcon

class Trainings_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    private val args: Trainings_ChooseExerciseFragmentArgs by navArgs()
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setXNavIcon()
        
        vm.exerciseAddedEvent.observe(viewLifecycleOwner, EventObserver{
            findNavController().popBackStack()
        })
    
        vm.setTrainingId(args.trainingId)
    
        exerciseAdapter.clickCallback = { exercise ->
            vm.addExerciseToTraining(exercise, args.trainingId, args.num)
        }
    }
}