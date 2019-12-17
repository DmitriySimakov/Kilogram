package com.dmitrysimakov.kilogram.ui.home.trainings.add_training_set

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.databinding.DialogAddTrainingSetBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.util.EventObserver
import com.dmitrysimakov.kilogram.util.hideKeyboard
import com.dmitrysimakov.kilogram.util.popBackStack
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTrainingSetDialog : BottomSheetDialogFragment() {
    
    private val args: AddTrainingSetDialogArgs by navArgs()
    
    private val vm: AddTrainingSetViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()

    private lateinit var binding: DialogAddTrainingSetBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogAddTrainingSetBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        vm.start(
                args.trainingExerciseId,
                args.setId,
                args.weight,
                args.reps,
                args.time,
                args.distance
        )
        
        vm.trainingSet.observe(viewLifecycleOwner, Observer {  })
        vm.trainingExercise.observe(viewLifecycleOwner, Observer {  })
        vm.trainingSetSavedEvent.observe(viewLifecycleOwner, EventObserver {
            sharedVM.onSetCompleted(vm.trainingExercise.value?.rest ?: 0)
            hideKeyboard()
            popBackStack()
        })
    }
}