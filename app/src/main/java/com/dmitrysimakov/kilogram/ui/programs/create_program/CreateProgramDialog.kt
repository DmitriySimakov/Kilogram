package com.dmitrysimakov.kilogram.ui.programs.create_program

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log.d
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.databinding.DialogCreateProgramBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import com.dmitrysimakov.kilogram.util.hideKeyboard
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.dialog_create_program.*
import javax.inject.Inject

class CreateProgramDialog : DaggerAppCompatDialogFragment(), ItemInsertedListener {

    private val TAG = this::class.java.simpleName

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: DialogCreateProgramBinding

    private lateinit var viewModel: CreateProgramViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        binding = DialogCreateProgramBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
                .setView(binding.root)
                .setTitle("Тренировочная программа")
                .setPositiveButton("Создать", null)
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
                .create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = getViewModel(viewModelFactory)
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
        
        name.setOnEditorActionListener { _, _, _ ->
            createProgram()
            true
        }
        
        activity?.fab?.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.dialog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.ok -> {
            createProgram()
            true
        }
        else -> false
    }
    
    private fun createProgram() {
        hideKeyboard()
        viewModel.createProgram(this)
    }

    @MainThread
    override fun onItemInserted(id: Long) {
        findNavController().navigate(CreateProgramDialogDirections.toChooseProgramDayFragment(id))
    }
}
