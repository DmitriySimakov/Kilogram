package com.dmitrysimakov.kilogram.ui.exercises.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_exercises.*
import javax.inject.Inject

class ExercisesFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    private lateinit var viewModel: ExercisesViewModel

    private lateinit var adapter: ExercisesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(viewModelFactory)
        viewModel.exerciseList.observe(this, Observer { adapter.submitList(it) })

        val params = ExercisesFragmentArgs.fromBundle(arguments!!)
        viewModel.setMuscle(params.muscleId)

        adapter = ExercisesAdapter(executors) { exercise ->
            val navController = findNavController()
            if (params.trainingId == 0L) {
                navController.navigate(ExercisesFragmentDirections
                        .toExerciseDetailFragment(exercise._id))
            } else {
                navController.navigate(ExercisesFragmentDirections
                        .toAddExerciseDialog(params.trainingId, exercise._id))
            }
        }

        exercises_rv.adapter = adapter

        activity?.fab?.hide()
    }
}