package com.dmitrysimakov.kilogram.ui.home.calendar_day_overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.databinding.DialogCalendarDayOverviewBinding
import com.dmitrysimakov.kilogram.ui.home.TrainingsAdapter
import com.dmitrysimakov.kilogram.util.toLocalDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarDayOverviewDialog : BottomSheetDialogFragment() {
    
    private val args: CalendarDayOverviewDialogArgs by navArgs()
    
    private val vm: CalendarDayOverviewViewModel by viewModel()
    
    private lateinit var binding: DialogCalendarDayOverviewBinding
    
    private val adapter by lazy {
        TrainingsAdapter {
            findNavController().navigate(CalendarDayOverviewDialogDirections
                    .toTrainingExercisesFragment(it._id, it.duration == null))
        }
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogCalendarDayOverviewBinding.inflate(inflater)
        binding.vm = vm
        binding.recyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.setDate(args.date.toLocalDate())
        
        vm.trainings.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}