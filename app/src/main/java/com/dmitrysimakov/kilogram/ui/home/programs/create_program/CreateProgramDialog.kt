package com.dmitrysimakov.kilogram.ui.home.programs.create_program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmitrysimakov.kilogram.databinding.DialogCreateProgramBinding
import com.dmitrysimakov.kilogram.ui.home.programs.create_program.CreateProgramDialogDirections.Companion.toChooseProgramDayFragment
import com.dmitrysimakov.kilogram.util.hideKeyboard
import com.dmitrysimakov.kilogram.util.live_data.EventObserver
import com.dmitrysimakov.kilogram.util.navigate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_create_program.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateProgramDialog : BottomSheetDialogFragment() {
    
    private lateinit var binding: DialogCreateProgramBinding

    private val vm: CreateProgramViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogCreateProgramBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        descriptionET.setOnFocusChangeListener { _, hasFocus ->
            descriptionTIL.isCounterEnabled = hasFocus
        }
        
        binding.createProgramBtn.setOnClickListener {
            if (validate()) vm.createProgram()
        }
        
        vm.programCreatedEvent.observe(viewLifecycleOwner, EventObserver {
            hideKeyboard()
            navigate(toChooseProgramDayFragment(it))
        })
    }
    
    private fun validate():Boolean {
        val nameIsEmpty = vm.name.value?.trim()?.isEmpty() ?: true
        nameTIL.error = "Заполните поле".takeIf { nameIsEmpty }
        return !nameIsEmpty
    }
}
