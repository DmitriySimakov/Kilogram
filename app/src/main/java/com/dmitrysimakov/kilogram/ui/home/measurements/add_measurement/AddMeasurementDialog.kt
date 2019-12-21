package com.dmitrysimakov.kilogram.ui.home.measurements.add_measurement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dmitrysimakov.kilogram.databinding.DialogAddMeasurementBinding
import com.dmitrysimakov.kilogram.util.popBackStack
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddMeasurementDialog : BottomSheetDialogFragment() {
    
    private val vm: AddMeasurementViewModel by viewModel()
    
    private val adapter by lazy { MeasurementInputAdapter() }
    
    private lateinit var binding: DialogAddMeasurementBinding
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogAddMeasurementBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        binding.recyclerView.adapter = adapter
        
        vm.measurements.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        
        binding.addMeasurementBtn.setOnClickListener {
            vm.addMeasurements()
            popBackStack()
        }
    }
}
