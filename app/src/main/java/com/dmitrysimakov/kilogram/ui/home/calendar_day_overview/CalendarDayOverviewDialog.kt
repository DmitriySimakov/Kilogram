package com.dmitrysimakov.kilogram.ui.home.calendar_day_overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dmitrysimakov.kilogram.databinding.DialogCalendarDayOverviewBinding
import com.dmitrysimakov.kilogram.ui.home.trainings.trainings.TrainingsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate

class CalendarDayOverviewDialog(private val date: LocalDate) : BottomSheetDialogFragment() {
    
    private val vm: CalendarDayOverviewViewModel by viewModel()
    
    private lateinit var binding: DialogCalendarDayOverviewBinding
    
    private val adapter by lazy { TrainingsAdapter{
        // TODO
    }}
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogCalendarDayOverviewBinding.inflate(inflater)
        binding.vm = vm
        binding.recyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.setDate(date)
        
        vm.trainings.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
    
    companion object {
        fun newInstance(date: LocalDate): CalendarDayOverviewDialog {
            return CalendarDayOverviewDialog(date)
        }
    }
}