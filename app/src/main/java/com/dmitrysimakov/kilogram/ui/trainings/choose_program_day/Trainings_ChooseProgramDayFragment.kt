package com.dmitrysimakov.kilogram.ui.trainings.choose_program_day

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_program_day.ChooseProgramDayFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class Trainings_ChooseProgramDayFragment : ChooseProgramDayFragment() {
    
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        val params = Trainings_ChooseProgramDayFragmentArgs.fromBundle(arguments!!)
        vm.setProgram(params.programId)
        
        adapter.clickCallback = { programDay ->
            sharedVM.programDayId.value = programDay._id
            findNavController().popBackStack(R.id.createTrainingFragment, false)
        }
        
        activity?.fab?.hide()
    }
}