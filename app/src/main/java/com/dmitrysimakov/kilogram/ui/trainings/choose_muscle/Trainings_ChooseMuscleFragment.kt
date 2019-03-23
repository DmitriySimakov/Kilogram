package com.dmitrysimakov.kilogram.ui.trainings.choose_muscle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.ChooseMuscleFragment
import com.dmitrysimakov.kilogram.util.runCircularRevealAnimation
import kotlinx.android.synthetic.main.app_bar_main.*

class Trainings_ChooseMuscleFragment : ChooseMuscleFragment() {
    
    private var wasPopped = true
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wasPopped = false
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (!wasPopped) runCircularRevealAnimation(activity!!.fab, view, R.color.grey200, R.color.white)
        wasPopped = true
        return view
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        val params = Trainings_ChooseMuscleFragmentArgs.fromBundle(arguments!!)
        
        adapter.clickCallback = { muscle ->
            findNavController().navigate(Trainings_ChooseMuscleFragmentDirections
                    .toChooseExerciseFragment(muscle._id, params.num, params.trainingId))
        }
    }
}