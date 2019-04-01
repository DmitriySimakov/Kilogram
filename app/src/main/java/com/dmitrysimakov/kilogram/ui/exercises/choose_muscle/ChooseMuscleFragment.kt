package com.dmitrysimakov.kilogram.ui.exercises.choose_muscle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_choose_muscle.*
import javax.inject.Inject

class ChooseMuscleFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    protected val viewModel by lazy { getViewModel<ChooseMuscleViewModel>(viewModelFactory) }

    protected val adapter by lazy { MuscleListAdapter(executors) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_choose_muscle, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        adapter.clickCallback = { muscle ->
            findNavController().navigate(ChooseMuscleFragmentDirections.toChooseExerciseFragment(muscle._id))
        }
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        viewModel.muscleList.observe(this, Observer { adapter.submitList(it) })
        
        activity?.fab?.hide()
    }
}
