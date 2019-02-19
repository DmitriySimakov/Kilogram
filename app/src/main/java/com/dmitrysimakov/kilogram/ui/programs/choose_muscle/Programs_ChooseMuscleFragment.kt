package com.dmitrysimakov.kilogram.ui.programs.choose_muscle

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.MuscleListAdapter
import kotlinx.android.synthetic.main.fragment_choose_muscle.*

class Programs_ChooseMuscleFragment : ChooseMuscleFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val params = Programs_ChooseMuscleFragmentArgs.fromBundle(arguments!!)
        
        adapter.setClickListener { muscle ->
            findNavController().navigate(Programs_ChooseMuscleFragmentDirections
                    .toChooseExerciseFragment(muscle._id, params.programDayId))
        }
    }
}