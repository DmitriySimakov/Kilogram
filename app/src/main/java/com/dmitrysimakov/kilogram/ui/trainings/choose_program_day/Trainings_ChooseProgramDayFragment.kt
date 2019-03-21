package com.dmitrysimakov.kilogram.ui.trainings.choose_program_day

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.MainViewModel
import com.dmitrysimakov.kilogram.ui.common.choose_program_day.ChooseProgramDayFragment
import com.dmitrysimakov.kilogram.util.getViewModel
import kotlinx.android.synthetic.main.app_bar_main.*

class Trainings_ChooseProgramDayFragment : ChooseProgramDayFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        val params = Trainings_ChooseProgramDayFragmentArgs.fromBundle(arguments!!)
        viewModel.setProgram(params.programId)
        
        adapter.clickCallback = { programDay ->
            val mainViewModel = getViewModel(activity!!, viewModelFactory)
            mainViewModel.programDayId.value = programDay._id
            findNavController().popBackStack(R.id.createTrainingFragment, false)
        }
        
        activity?.fab?.hide()
    }
}