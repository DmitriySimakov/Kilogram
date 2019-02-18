package com.dmitrysimakov.kilogram.ui.common.choose_exercise

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
import kotlinx.android.synthetic.main.fragment_choose_exercise.*
import javax.inject.Inject

class ChooseExerciseFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    private lateinit var viewModel: ChooseExerciseViewModel

    private lateinit var adapter: ExerciseListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_exercise, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(viewModelFactory)
        viewModel.exerciseList.observe(this, Observer { adapter.submitList(it) })

        val params = ChooseExerciseFragmentArgs.fromBundle(arguments!!)
        viewModel.setMuscle(params.muscleId)

        adapter = ExerciseListAdapter(executors) { exercise ->
            val navController = findNavController()
            if (params.trainingId == 0L && params.programDayId == 0L) {
                navController.navigate(ChooseExerciseFragmentDirections.toExerciseDetailFragment(exercise._id))
            } else {
                navController.navigate(ChooseExerciseFragmentDirections.toAddExerciseFragment(exercise._id, params.programDayId, params.trainingId))
            }
        }

        exercises_rv.adapter = adapter

        activity?.fab?.hide()
    }
}