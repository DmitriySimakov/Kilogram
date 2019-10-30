package com.dmitrysimakov.kilogram.ui.trainings.create_training

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.DialogCreateTrainingBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.common.ChipGroupFilterAdapter
import com.dmitrysimakov.kilogram.util.hideKeyboard
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.android.synthetic.main.app_bar_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CreateTrainingDialog : AppCompatDialogFragment() {
    
    private lateinit var binding: DialogCreateTrainingBinding

    private val vm: CreateTrainingViewModel by viewModel()
    
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private lateinit var muscleAdapter: ChipGroupFilterAdapter
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = DialogCreateTrainingBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity!!)
                .setView(binding.root)
                .setTitle("Тренировка")
                .setPositiveButton("Начать") { _, _ -> submit() }
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
                .create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding.vm = vm
        binding.lifecycleOwner = this

        setDatePickerDialog()
        setTimePickerDialog()
        return binding.root
    }
    
    

    private fun setDatePickerDialog() {
        binding.dateTv.setOnClickListener {
            val calendar = vm.calendar.value!!
            val curYear = calendar.get(Calendar.YEAR)
            val curMonthOfYear = calendar.get(Calendar.MONTH)
            val curDay = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                vm.updateCalendar()
            }, curYear, curMonthOfYear, curDay).apply {
                datePicker.minDate = 0
                val millisecondsPerMonth = 30L * 24 * 60 * 60 * 1000
                datePicker.maxDate = Date().time + millisecondsPerMonth
                show()
            }
        }
    }

    private fun setTimePickerDialog() {
        binding.timeTv.setOnClickListener{
            val calendar = vm.calendar.value!!
            val curHourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            val curMinute = calendar.get(Calendar.MINUTE)
            val dialog = TimePickerDialog(context, { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                vm.updateCalendar()
            }, curHourOfDay, curMinute, true)
            dialog.show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        if (dialog == null) {
            activity?.toolbar?.setNavigationIcon(R.drawable.ic_close_24dp)
            setHasOptionsMenu(true)
        }
    
        sharedVM.programDayId.observe(viewLifecycleOwner, Observer { if (it != null) vm.setProgramDay(it) })
        vm.byProgram.observe(viewLifecycleOwner, Observer {  })
        vm.programDay.observe(viewLifecycleOwner, Observer {
            if (it != null) vm.byProgram.setNewValue(true)
        })
        
        binding.programDayTV.setOnClickListener {
            findNavController().navigate(CreateTrainingDialogDirections.toChooseProgramFragment())
        }
    
        muscleAdapter = ChipGroupFilterAdapter(binding.musclesCG) { name, isChecked ->
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
        vm.createTraining { id ->
            if (vm.byProgram.value!!) {
                vm.fillTrainingWithProgramExercises(id)
            }
            vm.saveMuscles(id)
            findNavController().navigate(CreateTrainingDialogDirections.toExercisesFragment(id))
        }
        sharedVM.onTrainingSessionStarted()
    }
}
