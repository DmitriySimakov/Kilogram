package com.dmitrysimakov.kilogram.ui.catalog.programs.choose_exercise

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.ui.common.choose_exercise.ChooseExerciseFragment
import com.dmitrysimakov.kilogram.util.EventObserver
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setXNavIcon

class Programs_ChooseExerciseFragment : ChooseExerciseFragment() {
    
    private val args: Programs_ChooseExerciseFragmentArgs by navArgs()
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setXNavIcon()
        
        vm.setProgramDayId(args.programDayId)
        
        exerciseAdapter.clickCallback = { exercise ->
            vm.addExerciseToProgramDay(exercise, args.programDayId, args.num)
        }
        
        vm.exerciseAddedEvent.observe(viewLifecycleOwner, EventObserver{ popBackStack() })
    }
}