package com.dmitrysimakov.kilogram.ui.training.create_training

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.databinding.DialogCreateTrainingBinding
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerAppCompatDialogFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CreateTrainingDialog : DaggerAppCompatDialogFragment(), ItemInsertedListener {

    private val TAG = this::class.java.simpleName

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: DialogCreateTrainingBinding

    private lateinit var viewModel: CreateTrainingViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        binding = DialogCreateTrainingBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        d(TAG, "onCreateDialog")
        return AlertDialog.Builder(activity!!)
                .setView(binding.root)
                .setTitle("Тренировка")
                .setPositiveButton("Начать", null)
                .setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }
                .create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        d(TAG, "onCreateView")
        viewModel = getViewModel(viewModelFactory)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        setDatePickerDialog()
        setTimePickerDialog()
        return binding.root
    }

    private fun setDatePickerDialog() {
        binding.dateTv.setOnClickListener {
            val calendar = viewModel.calendar
            val curYear = calendar.get(Calendar.YEAR)
            val curMonthOfYear = calendar.get(Calendar.MONTH)
            val curDay = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val c = Calendar.getInstance()
                c.set(year, monthOfYear, dayOfMonth)
                viewModel.date.value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.time)
            }, curYear, curMonthOfYear, curDay).apply {
                datePicker.minDate = 0
                val millisecondsPerMonth = 30.toLong() * 24 * 60 * 60 * 1000
                datePicker.maxDate = Date().time + millisecondsPerMonth
                show()
            }
        }
    }

    private fun setTimePickerDialog() {
        binding.timeTv.setOnClickListener{
            val calendar = viewModel.calendar
            val curHourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            val curMinute = calendar.get(Calendar.MINUTE)
            val dialog = TimePickerDialog(context, { _, hourOfDay, minute ->
                val c = Calendar.getInstance()
                c.set(1970, 0, 1, hourOfDay, minute)
                viewModel.time.value = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(c.time)
            }, curHourOfDay, curMinute, true)
            dialog.show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        d(TAG, "onActivityCreated")
        if (dialog == null) {
            activity?.toolbar?.setNavigationIcon(R.drawable.baseline_close_white_24)
            setHasOptionsMenu(true)
        }

        activity?.fab?.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.dialog, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.ok -> {
                viewModel.createTraining(this)
                return true
            }
        }
        return false
    }

    @MainThread
    override fun onItemInserted(id: Long) {
        findNavController().navigate(CreateTrainingDialogDirections.toTrainingFragment(id))
    }
}
