package com.dmitrysimakov.kilogram.ui.home.trainings.create_training

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.databinding.DialogCreateTrainingBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.home.trainings.create_training.CreateTrainingDialogDirections.Companion.toChooseProgramFragment
import com.dmitrysimakov.kilogram.ui.home.trainings.create_training.CreateTrainingDialogDirections.Companion.toExercisesFragment
import com.dmitrysimakov.kilogram.util.hideKeyboard
import com.dmitrysimakov.kilogram.util.live_data.EventObserver
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.toOffsetDateTime
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.Year
import org.threeten.bp.ZoneOffset

class CreateTrainingDialog : BottomSheetDialogFragment() {
    
    private val args: CreateTrainingDialogArgs by navArgs()
    
    private lateinit var binding: DialogCreateTrainingBinding

    private val vm: CreateTrainingViewModel by viewModel()
    
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogCreateTrainingBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this

        setDatePickerDialog()
        setTimePickerDialog()
        return binding.root
    }
    
    private fun setDatePickerDialog() {
        binding.dateTv.setOnClickListener {
            val dateTime = vm.dateTime.value!!
            val listener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                vm.setDateTime(dateTime.withYear(year).withMonth(monthOfYear).withDayOfMonth(dayOfMonth))
            }
            DatePickerDialog(context!!, listener, dateTime.year, dateTime.monthValue, dateTime.dayOfMonth).apply {
                fun LocalDate.toEpochMilli() = atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
                datePicker.minDate = Year.of(2000).atDay(1).toEpochMilli()
                datePicker.maxDate = LocalDate.now().plusMonths(1).toEpochMilli()
                show()
            }
        }
    }

    private fun setTimePickerDialog() {
        binding.timeTv.setOnClickListener{
            val dateTime = vm.dateTime.value!!
            val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                vm.setDateTime(dateTime.withHour(hourOfDay).withMinute(minute))
            }
            TimePickerDialog(context, listener, dateTime.hour, dateTime.minute, true).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        args.date?.let { vm.setDateTime(it.toOffsetDateTime()) }
        
        sharedVM.programDayId.observe(viewLifecycleOwner) { if (it != null) vm.setProgramDay(it) }
        vm.byProgram.observe(viewLifecycleOwner) { }
        vm.programDay.observe(viewLifecycleOwner) {
            if (it != null) vm.byProgram.setNewValue(true)
        }
        
        binding.startTrainingBtn.setOnClickListener {
            vm.createTraining()
            sharedVM.onTrainingSessionStarted()
        }
        binding.programLayout.setOnClickListener { navigate(toChooseProgramFragment()) }
    
        vm.trainingCreatedEvent.observe(viewLifecycleOwner, EventObserver {
            hideKeyboard()
            navigate(toExercisesFragment(it, true))
        })
    }
    
    override fun onStop() {
        sharedVM.programDayId.value = 0L
        super.onStop()
    }
}
