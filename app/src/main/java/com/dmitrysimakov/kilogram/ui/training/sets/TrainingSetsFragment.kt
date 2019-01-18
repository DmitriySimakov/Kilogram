package com.dmitrysimakov.kilogram.ui.training.sets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

class TrainingSetsFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    private lateinit var viewModel: TrainingSetsViewModel

    //lateinit var adapter: TrainingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_training, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(viewModelFactory)
        val params = TrainingSetsFragmentArgs.fromBundle(arguments!!)

        //adapter = TrainingAdapter(executors) {}
        //exercises_rv.adapter = adapter
        //viewModel.exercises.observe(this, Observer { adapter.submitList(it) })

        /*ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val exercise = adapter.get(viewHolder.adapterPosition)
                viewModel.deleteExercise(exercise, params.trainingId)
            }
        }).attachToRecyclerView(exercises_rv)*/

        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(TrainingSetsFragmentDirections
                    .toAddSetDialog(params.trainingExerciseId))
        }
    }
}