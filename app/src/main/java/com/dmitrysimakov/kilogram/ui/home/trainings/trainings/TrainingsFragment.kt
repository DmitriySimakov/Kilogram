package com.dmitrysimakov.kilogram.ui.home.trainings.trainings

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_trainings.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.Year
import org.threeten.bp.ZoneOffset

class TrainingsFragment : Fragment() {
    
    private val vm: TrainingsViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()

    private val adapter by lazy { TrainingsAdapter { training ->
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
        vm.detailedTrainings.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val training = adapter.getItem(viewHolder.adapterPosition)
                vm.deleteTraining(training._id)
                sharedVM.onTrainingSessionFinished()
            }
        }).attachToRecyclerView(recyclerView)

        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            if (sharedVM.timerIsRunning.value!!) {
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
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.calendar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.calendarView -> {
            val date = LocalDate.now()
            DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val d = date.withYear(year).withMonth(monthOfYear).withDayOfMonth(dayOfMonth)
                (recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                        vm.findPositionInTrainingList(d), 0)
            }, date.year, date.monthValue, date.dayOfMonth).apply {
                fun LocalDate.toEpochMilli() = atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
                datePicker.minDate = Year.of(2000).atDay(1).toEpochMilli()
                datePicker.maxDate = LocalDate.now().plusMonths(1).toEpochMilli()
                show()
            }
            true
        }
        else -> false
    }
}
