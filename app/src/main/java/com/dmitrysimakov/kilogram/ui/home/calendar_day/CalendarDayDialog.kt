package com.dmitrysimakov.kilogram.ui.home.calendar_day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.databinding.DialogCalendarDayBinding
import com.dmitrysimakov.kilogram.ui.home.calendar_day.CalendarDayDialogDirections.Companion.toCreateTrainingDialog
import com.dmitrysimakov.kilogram.ui.home.calendar_day.CalendarDayDialogDirections.Companion.toTrainingExercisesFragment
import com.dmitrysimakov.kilogram.ui.home.trainings.TrainingsAdapter
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.toDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarDayDialog : BottomSheetDialogFragment() {
    
    private val args: CalendarDayDialogArgs by navArgs()
    
    private val vm: CalendarDayViewModel by viewModel()
    
    private lateinit var binding: DialogCalendarDayBinding
    
    private val adapter by lazy { TrainingsAdapter {
            navigate(toTrainingExercisesFragment(it.id, it.duration == null))
    }}
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogCalendarDayBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerView.adapter = adapter
        binding.addTrainingButton.setOnClickListener { navigate(toCreateTrainingDialog(args.date)) }
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        vm.date.setNewValue(args.date.toDate())
        vm.trainings.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}