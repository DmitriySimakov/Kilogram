package com.dmitrysimakov.kilogram.ui.exercises.choose_muscle

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.MuscleListAdapter
import kotlinx.android.synthetic.main.fragment_choose_muscle.*

class Exercises_ChooseMuscleFragment : ChooseMuscleFragment() {
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        
        adapter.clickCallback = { muscle ->
            findNavController().navigate(Exercises_ChooseMuscleFragmentDirections
                    .toChooseExerciseFragment(muscle._id))
        }
    }
}