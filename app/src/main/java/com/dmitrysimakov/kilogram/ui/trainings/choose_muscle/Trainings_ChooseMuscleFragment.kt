package com.dmitrysimakov.kilogram.ui.trainings.choose_muscle

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.MuscleListAdapter
import kotlinx.android.synthetic.main.fragment_choose_muscle.*

class Trainings_ChooseMuscleFragment : ChooseMuscleFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val params = Trainings_ChooseMuscleFragmentArgs.fromBundle(arguments!!)
        
        adapter.setClickListener { muscle ->
            findNavController().navigate(Trainings_ChooseMuscleFragmentDirections
                    .toChooseExerciseFragment(muscle._id, params.num, params.trainingId))
        }
    }
}