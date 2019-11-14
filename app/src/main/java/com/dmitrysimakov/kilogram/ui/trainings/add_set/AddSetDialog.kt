package com.dmitrysimakov.kilogram.ui.trainings.add_set

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.DialogAddSetBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.util.hideKeyboard
import kotlinx.android.synthetic.main.app_bar_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddSetDialog : Fragment() {
    
    private val args: AddSetDialogArgs by navArgs()
    
    private val vm: AddSetViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()

    private lateinit var binding: DialogAddSetBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogAddSetBinding.inflate(inflater)
        binding.viewModel = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        activity?.toolbar?.setNavigationIcon(R.drawable.ic_close_24dp)
        setHasOptionsMenu(true)
    
        vm.setTrainingExercise(args.trainingExerciseId)
        vm.setSet(args.setId, args.weight, args.reps, args.time, args.distance)
        vm.set.observe(viewLifecycleOwner, Observer {  })
        vm.trainingExercise.observe(viewLifecycleOwner, Observer {  })

        activity?.fab?.hide()
    }
    
    override fun onStop() {
        hideKeyboard()
        super.onStop()
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dialog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.ok -> {
            submit()
            true
        }
        else -> false
    }
    
    private fun submit() {
        if (args.setId == 0L) {
            vm.addSet(sharedVM.elapsedSessionTime.value ?: 0)
            sharedVM.onSetCompleted(vm.trainingExercise.value?.rest ?: 0)
        } else {
            vm.updateSet()
        }
        findNavController().popBackStack()
    }
}