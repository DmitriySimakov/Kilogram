package com.dmitrysimakov.kilogram.ui.home.calendar_day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.databinding.DialogCalendarDayBinding
import com.dmitrysimakov.kilogram.ui.home.TrainingsAdapter
import com.dmitrysimakov.kilogram.ui.home.calendar_day.CalendarDayDialogDirections.Companion.toCreateTrainingDialog
import com.dmitrysimakov.kilogram.ui.home.calendar_day.CalendarDayDialogDirections.Companion.toTrainingExercisesFragment
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.toIsoString
import com.dmitrysimakov.kilogram.util.toLocalDate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.OffsetTime

class CalendarDayDialog : BottomSheetDialogFragment() {
    
    private val args: CalendarDayDialogArgs by navArgs()
    
    private val vm: CalendarDayViewModel by viewModel()
    
    private lateinit var binding: DialogCalendarDayBinding
    
    private val adapter by lazy { TrainingsAdapter {
        navigate(toTrainingExercisesFragment(it._id, it.duration == null))
    }}
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogCalendarDayBinding.inflate(inflater)
        binding.vm = vm
        binding.recyclerView.adapter = adapter
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val date = args.date.toLocalDate()
        vm.setDate(date)
        
        vm.trainings.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        
        binding.addTrainingButton.setOnClickListener {
            navigate(toCreateTrainingDialog(date.atTime(OffsetTime.now()).toIsoString()))
        }
    }
}