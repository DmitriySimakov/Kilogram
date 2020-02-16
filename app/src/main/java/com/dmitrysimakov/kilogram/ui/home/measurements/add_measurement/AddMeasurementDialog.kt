package com.dmitrysimakov.kilogram.ui.home.measurements.add_measurement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.databinding.DialogAddMeasurementBinding
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.toDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddMeasurementDialog : BottomSheetDialogFragment() {
    
    private val args: AddMeasurementDialogArgs by navArgs()
    
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
        binding.addMeasurementBtn.setOnClickListener {
            vm.addMeasurements()
            popBackStack()
        }
        
        vm.date.setNewValue(args.date.toDate())
        vm.measurements.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}
