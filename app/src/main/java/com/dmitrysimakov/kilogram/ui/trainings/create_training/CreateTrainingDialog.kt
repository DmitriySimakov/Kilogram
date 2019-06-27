package com.dmitrysimakov.kilogram.ui.trainings.create_training

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.databinding.DialogCreateTrainingBinding
import com.dmitrysimakov.kilogram.ui.common.ChipGroupFilterAdapter
import com.dmitrysimakov.kilogram.util.getViewModel
import com.dmitrysimakov.kilogram.util.hideKeyboard
import com.dmitrysimakov.kilogram.util.setNewValue
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import javax.inject.Inject

class CreateTrainingDialog : DaggerAppCompatDialogFragment(), ItemInsertedListener {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: DialogCreateTrainingBinding

    private val viewModel by lazy { getViewModel<CreateTrainingViewModel>(viewModelFactory) }
    
    private val mainViewModel by lazy { getViewModel(activity!!, viewModelFactory) }
    
    private lateinit var muscleAdapter: ChipGroupFilterAdapter
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        
        mainViewModel.programDayId.observe(this, Observer { if (it != null) viewModel.setProgramDay(it) })
        viewModel.byProgram.observe(this, Observer {  })
        viewModel.programDay.observe(this, Observer {
            if (it != null) viewModel.byProgram.setNewValue(true)
        })
        
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
        binding.vm = viewModel
        binding.lifecycleOwner = this

        setDatePickerDialog()
        setTimePickerDialog()
        return binding.root
    }

    private fun setDatePickerDialog() {
        binding.dateTv.setOnClickListener {
            val calendar = viewModel.calendar.value!!
            val curYear = calendar.get(Calendar.YEAR)
            val curMonthOfYear = calendar.get(Calendar.MONTH)
            val curDay = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                viewModel.updateCalendar()
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
            val calendar = viewModel.calendar.value!!
            val curHourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            val curMinute = calendar.get(Calendar.MINUTE)
            val dialog = TimePickerDialog(context, { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                viewModel.updateCalendar()
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
        
        binding.programDayTV.setOnClickListener {
            findNavController().navigate(CreateTrainingDialogDirections.toChooseProgramFragment())
        }
    
        muscleAdapter = ChipGroupFilterAdapter(binding.musclesCG) { id, isChecked ->
            viewModel.muscleList.value?.find{ it._id == id }?.is_active = isChecked
        }
        viewModel.muscleList.observe(this, Observer { muscleAdapter.submitList(it) })
        
        activity?.fab?.hide()
    }
    
    override fun onStop() {
        hideKeyboard()
        mainViewModel.programDayId.value = 0L
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
        viewModel.createTraining(this)
        mainViewModel.onTrainingSessionStarted()
    }

    @MainThread
    override fun onItemInserted(id: Long) {
        if (viewModel.byProgram.value!!) {
            viewModel.fillTrainingWithProgramExercises(id)
        }
        viewModel.saveMuscles(id)
        findNavController().navigate(CreateTrainingDialogDirections.toExercisesFragment(id))
    }
}
