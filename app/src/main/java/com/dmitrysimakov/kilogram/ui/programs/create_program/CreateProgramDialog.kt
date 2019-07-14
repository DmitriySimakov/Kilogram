package com.dmitrysimakov.kilogram.ui.programs.create_program

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.DialogCreateProgramBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import com.dmitrysimakov.kilogram.util.hideKeyboard
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.dialog_create_program.*
import javax.inject.Inject

class CreateProgramDialog : DaggerAppCompatDialogFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: DialogCreateProgramBinding

    private val viewModel by lazy { getViewModel<CreateProgramViewModel>(viewModelFactory) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogCreateProgramBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
                .setView(binding.root)
                .setTitle("Тренировочная программа")
                .setPositiveButton("Создать") { _, _ -> submit() }
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
                .create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding.viewModel = viewModel
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
            viewModel.createProgram { id ->
                findNavController().navigate(CreateProgramDialogDirections.toChooseProgramDayFragment(id))
            }
        }
    }
    
    private fun validate():Boolean {
        val nameIsEmpty = nameTIL.editText?.text.toString().trim().isEmpty()
        nameTIL.error = "Заполните поле".takeIf { nameIsEmpty }
        return !nameIsEmpty
    }
}
