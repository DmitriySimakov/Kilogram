package com.dmitrysimakov.kilogram.ui.training.trainingList

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
import kotlinx.android.synthetic.main.fragment_trainings.*
import javax.inject.Inject

class TrainingsFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    private lateinit var viewModel: TrainingsViewModel

    lateinit var adapter: TrainingsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_trainings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(viewModelFactory)
        viewModel.trainingList.observe(this, Observer { adapter.submitList(it) })

        adapter = TrainingsListAdapter(executors) {}

        trainings_rv.adapter = adapter

        activity?.fab?.setOnClickListener{
            findNavController().navigate(R.id.createTrainingFragment)
        }
    }
}
