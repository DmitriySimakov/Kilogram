package com.dmitrysimakov.kilogram.ui.common.choose_program_day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_choose_program_day.*
import javax.inject.Inject

abstract class ChooseProgramDayFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    protected val viewModel by lazy { getViewModel<ChooseProgramDayViewModel>(viewModelFactory) }
    
    protected val adapter by lazy { ChooseProgramDayAdapter(executors) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_program_day, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        viewModel.programDays.observe(this, Observer { adapter.submitList(it) })
    }
}
