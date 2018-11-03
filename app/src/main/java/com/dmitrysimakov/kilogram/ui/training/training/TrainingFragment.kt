package com.dmitrysimakov.kilogram.ui.training.training

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.AppExecutors
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class TrainingFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    private lateinit var viewModel: TrainingViewModel

    lateinit var adapter: TrainingListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_training, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
/*
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TrainingViewModel::class.java)

        adapter = TrainingListAdapter(executors) {
            //CreateTrainingDialog().show(childFragmentManager, null)
        }

        exercises_rv.adapter = adapter

        activity?.fab?.setOnClickListener{

        }*/
    }
}
