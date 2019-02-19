package com.dmitrysimakov.kilogram.ui.trainings.choose_exercise

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ExerciseListAdapter
import kotlinx.android.synthetic.main.fragment_choose_exercise.*

class Trainings_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val params = Trainings_ChooseExerciseFragmentArgs.fromBundle(arguments!!)
        viewModel.setMuscle(params.muscleId)
        
        adapter.setClickListener { exercise ->
            findNavController().navigate(Trainings_ChooseExerciseFragmentDirections
                    .toAddExerciseFragment(exercise._id, params.trainingId))
        }
    }
}