package com.dmitrysimakov.kilogram.ui.home.trainings.choose_program

import android.os.Bundle
import com.dmitrysimakov.kilogram.ui.common.choose_program.ChooseProgramFragment
import com.dmitrysimakov.kilogram.ui.home.trainings.choose_program.Trainings_ChooseProgramFragmentDirections.Companion.toChooseProgramDayFragment
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.setXNavIcon

class Trainings_ChooseProgramFragment : ChooseProgramFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        adapter.clickCallback = { navigate(toChooseProgramDayFragment(it._id)) }
     
        setXNavIcon()
    }
}