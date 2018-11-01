package com.dmitrysimakov.kilogram.ui.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.AppExecutors
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_choose_muscle.*
import javax.inject.Inject

class ChooseMuscleFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    private lateinit var viewModel: ChooseMuscleViewModel

    private lateinit var adapter: ChooseMuscleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_muscle, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ChooseMuscleViewModel::class.java)
        viewModel.muscleList.observe(this, Observer { adapter.submitList(it) })

        adapter = ChooseMuscleAdapter(executors) {muscle -> findNavController().navigate(
                ChooseMuscleFragmentDirections.toExercisesFragment(muscle._id.toInt()))
        }

        choose_exercises_rv.adapter = adapter
    }
}
