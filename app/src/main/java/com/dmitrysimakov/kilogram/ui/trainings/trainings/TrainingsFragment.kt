package com.dmitrysimakov.kilogram.ui.trainings.trainings

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_trainings.*
import java.util.*
import javax.inject.Inject

class TrainingsFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    private val viewModel by lazy { getViewModel<TrainingsViewModel>(viewModelFactory) }
    private val mainViewModel by lazy { getViewModel(activity!!, viewModelFactory) }

    private val adapter by lazy { TrainingsAdapter(mainViewModel.elapsedSessionTime, this, executors) {training ->
        findNavController().navigate(TrainingsFragmentDirections
            .toExercisesFragment(training._id, training.duration == null))
    }}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_trainings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        viewModel.trainingList.observe(this, Observer { adapter.submitList(it) })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val training = adapter.getItem(viewHolder.adapterPosition)
                viewModel.deleteTraining(training._id)
                getViewModel(activity!!, viewModelFactory).onTrainingSessionFinished()
            }
        }).attachToRecyclerView(recyclerView)

        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            if (mainViewModel.timerIsRunning.value!!) {
                AlertDialog.Builder(activity!!)
                        .setTitle("Завершите начатую тренировку")
                        .setNegativeButton("ОК") { dialog, _ -> dialog.cancel() }
                        .create()
                        .show()
            } else {
                findNavController().navigate(TrainingsFragmentDirections.toCreateTrainingFragment())
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.calendar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.calendar -> {
            val calendar = Calendar.getInstance()
            val curYear = calendar.get(Calendar.YEAR)
            val curMonthOfYear = calendar.get(Calendar.MONTH)
            val curDay = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                        viewModel.findPositionInTrainingList(calendar), 0)
            }, curYear, curMonthOfYear, curDay).apply {
                datePicker.minDate = 0
                val millisecondsPerMonth = 30L * 24 * 60 * 60 * 1000
                datePicker.maxDate = Date().time + millisecondsPerMonth
                show()
            }
            true
        }
        else -> false
    }
}
