package com.dmitrysimakov.kilogram.ui.common.choose_program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_choose_program.*
import javax.inject.Inject

abstract class ChooseProgramFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    protected lateinit var viewModel: ChooseProgramViewModel
    
    protected lateinit var adapter: ChooseProgramAdapter
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_program, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        viewModel = getViewModel(viewModelFactory)
        adapter = ChooseProgramAdapter(executors)
        recyclerView.adapter = adapter
        viewModel.programList.observe(this, Observer { adapter.submitList(it) })
    }
}
