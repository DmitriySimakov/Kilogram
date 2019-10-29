package com.dmitrysimakov.kilogram.ui.trainings.add_set

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.DialogAddSetBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.util.hideKeyboard
import kotlinx.android.synthetic.main.app_bar_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddSetDialog : AppCompatDialogFragment() {
    
    private val vm: AddSetViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()

    private lateinit var binding: DialogAddSetBinding

    private val params by lazy { AddSetDialogArgs.fromBundle(arguments!!) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogAddSetBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
                .setView(binding.root)
                .setTitle("Добавить подход")
                .setPositiveButton("Добавить") { _, _ -> submit() }
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
                .create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding.viewModel = vm
        binding.lifecycleOwner = this

        vm.setTrainingExercise(params.trainingExerciseId)
        vm.setSet(params.setId, params.weight, params.reps, params.time, params.distance)
        vm.set.observe(viewLifecycleOwner, Observer {  })
        vm.trainingExercise.observe(viewLifecycleOwner, Observer {  })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (dialog == null) {
            activity?.toolbar?.setNavigationIcon(R.drawable.ic_close_24dp)
            setHasOptionsMenu(true)
        }

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
        if (params.setId == 0L) {
            vm.addSet(sharedVM.elapsedSessionTime.value ?: 0)
            sharedVM.onSetCompleted(vm.trainingExercise.value?.rest ?: 0)
        } else {
            vm.updateSet()
        }
        findNavController().popBackStack()
    }
}