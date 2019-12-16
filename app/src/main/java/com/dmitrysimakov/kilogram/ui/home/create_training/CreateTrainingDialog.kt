package com.dmitrysimakov.kilogram.ui.home.create_training

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.DialogCreateTrainingBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.common.ChipGroupFilterAdapter
import com.dmitrysimakov.kilogram.util.EventObserver
import com.dmitrysimakov.kilogram.util.hideKeyboard
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.setXNavIcon
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.Year
import org.threeten.bp.ZoneOffset

class CreateTrainingDialog : Fragment() {
    
    private lateinit var binding: DialogCreateTrainingBinding

    private val vm: CreateTrainingViewModel by viewModel()
    
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private lateinit var muscleAdapter: ChipGroupFilterAdapter
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setXNavIcon()
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
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                vm.setDateTime(dateTime.withYear(year).withMonth(monthOfYear).withDayOfMonth(dayOfMonth))
            }, dateTime.year, dateTime.monthValue, dateTime.dayOfMonth).apply {
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
            val dialog = TimePickerDialog(context, { _, hourOfDay, minute ->
                vm.setDateTime(dateTime.withHour(hourOfDay).withMinute(minute))
            }, dateTime.hour, dateTime.minute, true)
            dialog.show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        setHasOptionsMenu(true)
    
        setupNavigation()
        sharedVM.programDayId.observe(viewLifecycleOwner, Observer { if (it != null) vm.setProgramDay(it) })
        vm.byProgram.observe(viewLifecycleOwner, Observer {  })
        vm.programDay.observe(viewLifecycleOwner, Observer {
            if (it != null) vm.byProgram.setNewValue(true)
        })
        
        binding.programDayTV.setOnClickListener {
            findNavController().navigate(CreateTrainingDialogDirections.toChooseProgramFragment())
        }
    
        muscleAdapter = ChipGroupFilterAdapter(binding.targetsCG) { name, isChecked ->
            vm.muscleList.value?.find{ it.name == name }?.is_active = isChecked
        }
        vm.muscleList.observe(viewLifecycleOwner, Observer { muscleAdapter.submitList(it) })
        
        activity?.fab?.hide()
    }
    
    override fun onStop() {
        hideKeyboard()
        sharedVM.programDayId.value = 0L
        super.onStop()
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dialog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.ok -> {
            submit()
            true
        }
        else -> false
    }
    
    private fun submit() {
        vm.createTraining()
        sharedVM.onTrainingSessionStarted()
    }
    
    private fun setupNavigation() {
        vm.trainingCreatedEvent.observe(viewLifecycleOwner, EventObserver{
            findNavController().navigate(CreateTrainingDialogDirections.toExercisesFragment(it, true))
        })
    }
}
