package com.dmitrysimakov.kilogram.ui.programs.create_program_day

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.databinding.DialogCreateProgramDayBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import com.dmitrysimakov.kilogram.util.hideKeyboard
import com.dmitrysimakov.kilogram.util.runCircularRevealAnimation
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.dialog_create_program_day.*
import javax.inject.Inject

class CreateProgramDayDialog : DaggerAppCompatDialogFragment(), ItemInsertedListener {

    private val TAG = this::class.java.simpleName

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: DialogCreateProgramDayBinding
    
    private val params by lazy { CreateProgramDayDialogArgs.fromBundle(arguments!!) }
    
    private val viewModel by lazy { getViewModel<CreateProgramDayViewModel>(viewModelFactory) }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        binding = DialogCreateProgramDayBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
                .setView(binding.root)
                .setTitle("День тренировочной программы")
                .setPositiveButton("Создать", null)
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
                .create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        runCircularRevealAnimation(activity!!.fab, binding.root, R.color.grey200, R.color.white)
        viewModel.setProgram(params.programId)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (dialog == null) {
            activity?.toolbar?.setNavigationIcon(R.drawable.baseline_close_white_24)
            setHasOptionsMenu(true)
        }
    
        descriptionET.setOnFocusChangeListener { _, hasFocus ->
            descriptionTIL.isCounterEnabled = hasFocus
        }
        
        activity?.fab?.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.dialog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.ok -> {
            if (validate()) {
                hideKeyboard()
                viewModel.createProgramDay(params.num, this)
            }
            true
        }
        else -> false
    }
    
    private fun validate():Boolean {
        val nameIsEmpty = nameTIL.editText?.text.toString().trim().isEmpty()
        nameTIL.error = "Заполните поле".takeIf { nameIsEmpty }
        return !nameIsEmpty
    }
    
    @MainThread
    override fun onItemInserted(id: Long) {
        findNavController().navigate(CreateProgramDayDialogDirections.toExercisesFragment(id))
    }
}
