package com.dmitrysimakov.kilogram.ui.home.trainings.choose_exercise

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.util.EventObserver
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setXNavIcon

class Trainings_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    private val args: Trainings_ChooseExerciseFragmentArgs by navArgs()
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setXNavIcon()
        
        exerciseAdapter.clickCallback = { exercise ->
            vm.addExerciseToTraining(exercise, args.trainingId, args.num)
        }
    
        vm.exerciseAddedEvent.observe(viewLifecycleOwner, EventObserver{ popBackStack() })
    }
}