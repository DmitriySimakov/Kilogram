package com.dmitrysimakov.kilogram.ui.trainings.choose_program

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_program.ChooseProgramFragment
import kotlinx.android.synthetic.main.app_bar_main.*

class Trainings_ChooseProgramFragment : ChooseProgramFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        adapter.clickCallback = { program ->
            findNavController().navigate(Trainings_ChooseProgramFragmentDirections
                    .toChooseProgramDayFragment(program._id))
        }
        
        activity?.fab?.hide()
    }
}