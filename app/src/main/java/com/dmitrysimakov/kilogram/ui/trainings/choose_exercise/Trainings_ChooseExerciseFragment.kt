package com.dmitrysimakov.kilogram.ui.trainings.choose_exercise

import android.content.Context
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.util.hideKeyboard

class Trainings_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    private val args: Trainings_ChooseExerciseFragmentArgs by navArgs()
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        
        vm.setTrainingId(args.trainingId)
    
        exerciseAdapter.clickCallback = { exercise ->
            hideKeyboard()
            findNavController().navigate(Trainings_ChooseExerciseFragmentDirections
                    .toAddExerciseFragment(exercise.name, args.num, args.trainingId))
        }
    }
}