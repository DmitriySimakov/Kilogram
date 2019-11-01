package com.dmitrysimakov.kilogram.ui.programs.create_program_day

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.DialogCreateProgramDayBinding
import com.dmitrysimakov.kilogram.ui.common.ChipGroupFilterAdapter
import com.dmitrysimakov.kilogram.util.hideKeyboard
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.dialog_create_program_day.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateProgramDayDialog : AppCompatDialogFragment() {
    
    private lateinit var binding: DialogCreateProgramDayBinding
    
    private lateinit var muscleAdapter: ChipGroupFilterAdapter
    
    private val args: CreateProgramDayDialogArgs by navArgs()
    
    private val vm: CreateProgramDayViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogCreateProgramDayBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
                .setView(binding.root)
                .setTitle("День тренировочной программы")
                .setPositiveButton("Создать") { _, _ -> submit() }
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
                .create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        vm.setProgram(args.programId)
        binding.viewModel = vm
        binding.lifecycleOwner = this
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (dialog == null) {
            activity?.toolbar?.setNavigationIcon(R.drawable.ic_close_24dp)
            setHasOptionsMenu(true)
        }
    
        descriptionET.setOnFocusChangeListener { _, hasFocus ->
            descriptionTIL.isCounterEnabled = hasFocus
        }
    
        muscleAdapter = ChipGroupFilterAdapter(binding.musclesCG) { name, isChecked ->
            vm.muscleList.value?.find{ it.name == name }?.is_active = isChecked
        }
        vm.muscleList.observe(viewLifecycleOwner, Observer { muscleAdapter.submitList(it) })
        
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
        if (validate()) {
            vm.createProgramDay(args.num) { id ->
                vm.saveMuscles(id)
                findNavController().navigate(CreateProgramDayDialogDirections.toExercisesFragment(id))
            }
        }
    }
    
    private fun validate():Boolean {
        val nameIsEmpty = nameTIL.editText?.text.toString().trim().isEmpty()
        nameTIL.error = "Заполните поле".takeIf { nameIsEmpty }
        return !nameIsEmpty
    }
}
