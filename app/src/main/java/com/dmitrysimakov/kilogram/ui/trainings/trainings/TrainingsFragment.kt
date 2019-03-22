package com.dmitrysimakov.kilogram.ui.trainings.trainings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_trainings.*
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
        return inflater.inflate(R.layout.fragment_trainings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        trainings_rv.adapter = adapter
        viewModel.trainingList.observe(this, Observer { adapter.submitList(it) })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val training = adapter.getItem(viewHolder.adapterPosition)
                viewModel.deleteTraining(training._id)
                getViewModel(activity!!, viewModelFactory).onTrainingSessionFinished()
            }
        }).attachToRecyclerView(trainings_rv)

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
}
