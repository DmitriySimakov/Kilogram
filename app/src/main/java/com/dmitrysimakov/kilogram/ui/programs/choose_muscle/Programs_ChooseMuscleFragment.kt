package com.dmitrysimakov.kilogram.ui.programs.choose_muscle

import android.content.Context
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.ChooseMuscleFragment
import com.dmitrysimakov.kilogram.ui.common.choose_muscle.MuscleListAdapter
import com.dmitrysimakov.kilogram.util.runCircularRevealAnimation
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_choose_muscle.*

class Programs_ChooseMuscleFragment : ChooseMuscleFragment() {
    
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
        val params = Programs_ChooseMuscleFragmentArgs.fromBundle(arguments!!)
        
        adapter.clickCallback = { muscle ->
            findNavController().navigate(Programs_ChooseMuscleFragmentDirections
                    .toChooseExerciseFragment(muscle._id, params.num, params.programDayId))
        }
    }
}