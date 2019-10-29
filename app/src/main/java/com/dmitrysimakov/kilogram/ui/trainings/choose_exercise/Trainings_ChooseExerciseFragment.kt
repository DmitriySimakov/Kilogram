package com.dmitrysimakov.kilogram.ui.trainings.choose_exercise

import android.content.Context
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.util.hideKeyboard

class Trainings_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        
        val params = Trainings_ChooseExerciseFragmentArgs.fromBundle(arguments!!)
        vm.setTraining(params.trainingId)
    
        exerciseAdapter.clickCallback = { exercise ->
            hideKeyboard()
            findNavController().navigate(Trainings_ChooseExerciseFragmentDirections
                    .toAddExerciseFragment(exercise._id, params.num, params.trainingId))
        }
    }
}