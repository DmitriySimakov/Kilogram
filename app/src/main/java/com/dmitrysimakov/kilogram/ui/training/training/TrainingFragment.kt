package com.dmitrysimakov.kilogram.ui.training.training

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
import kotlinx.android.synthetic.main.fragment_training.*
import javax.inject.Inject

class TrainingFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    private lateinit var viewModel: TrainingViewModel

    lateinit var adapter: TrainingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_training, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(viewModelFactory)
        val params = TrainingFragmentArgs.fromBundle(arguments!!)
        viewModel.setTraining(params.trainingId)

        viewModel.exercises.observe(this, Observer { adapter.submitList(it) })
        adapter = TrainingAdapter(executors) {}
        exercises_rv.adapter = adapter

        activity?.fab?.setOnClickListener{
            findNavController().navigate(TrainingFragmentDirections.toChooseMuscleFragment(params.trainingId))
        }
    }
}
