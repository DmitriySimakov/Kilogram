package com.dmitrysimakov.kilogram.ui.catalog.programs.create_program

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.DialogCreateProgramBinding
import com.dmitrysimakov.kilogram.ui.catalog.programs.create_program.CreateProgramDialogDirections.Companion.toChooseProgramDayFragment
import com.dmitrysimakov.kilogram.util.EventObserver
import com.dmitrysimakov.kilogram.util.hideKeyboard
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.setXNavIcon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_create_program.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateProgramDialog : Fragment() {
    
    private lateinit var binding: DialogCreateProgramBinding

    private val vm: CreateProgramViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setXNavIcon()
        binding = DialogCreateProgramBinding.inflate(inflater)
        binding.viewModel = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        setHasOptionsMenu(true)
        
        descriptionET.setOnFocusChangeListener { _, hasFocus ->
            descriptionTIL.isCounterEnabled = hasFocus
        }
        
        vm.programCreatedEvent.observe(viewLifecycleOwner, EventObserver{
            navigate(toChooseProgramDayFragment(it))
        })
        
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
            if (validate()) vm.createProgram()
            true
        }
        else -> false
    }
    
    private fun validate():Boolean {
        val nameIsEmpty = nameTIL.editText?.text.toString().trim().isEmpty()
        nameTIL.error = "Заполните поле".takeIf { nameIsEmpty }
        return !nameIsEmpty
    }
}
