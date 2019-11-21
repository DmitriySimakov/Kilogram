package com.dmitrysimakov.kilogram.ui.home.trainings.choose_program

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.ui.common.choose_program.ChooseProgramFragment
import com.dmitrysimakov.kilogram.util.setXNavIcon
import kotlinx.android.synthetic.main.activity_main.*

class Trainings_ChooseProgramFragment : ChooseProgramFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        adapter.clickCallback = { program ->
            findNavController().navigate(Trainings_ChooseProgramFragmentDirections
                    .toChooseProgramDayFragment(program._id))
        }
     
        setXNavIcon()
        activity?.fab?.hide()
    }
}