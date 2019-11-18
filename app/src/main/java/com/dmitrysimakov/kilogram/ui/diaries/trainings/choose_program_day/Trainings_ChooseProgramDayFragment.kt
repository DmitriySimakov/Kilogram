package com.dmitrysimakov.kilogram.ui.diaries.trainings.choose_program_day

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_program_day.ChooseProgramDayFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class Trainings_ChooseProgramDayFragment : ChooseProgramDayFragment() {
    
    private val args: Trainings_ChooseProgramDayFragmentArgs by navArgs()
    
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        vm.setProgramId(args.programId)
        
        adapter.clickCallback = { programDay ->
            sharedVM.programDayId.value = programDay._id
            findNavController().popBackStack(R.id.createTrainingFragment, false)
        }
        
        activity?.fab?.hide()
    }
}