package com.dmitrysimakov.kilogram.ui.common.choose_muscle

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
import kotlinx.android.synthetic.main.fragment_choose_muscle.*
import javax.inject.Inject

abstract class ChooseMuscleFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    protected lateinit var viewModel: ChooseMuscleViewModel

    protected lateinit var adapter: MuscleListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_muscle, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        adapter = MuscleListAdapter(executors)
        recyclerView.adapter = adapter
    
        viewModel = getViewModel(viewModelFactory)
        viewModel.muscleList.observe(this, Observer { adapter.submitList(it) })
        
        activity?.fab?.hide()
    }
}
