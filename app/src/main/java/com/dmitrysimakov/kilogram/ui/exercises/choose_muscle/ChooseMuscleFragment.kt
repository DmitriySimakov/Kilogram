package com.dmitrysimakov.kilogram.ui.exercises.choose_muscle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.AppExecutors
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_choose_muscle.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseMuscleFragment : Fragment() {

    protected val executors: AppExecutors by inject()

    protected val vm: ChooseMuscleViewModel by viewModel()

    protected val adapter by lazy { MuscleListAdapter(executors) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_choose_muscle, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        adapter.clickCallback = { muscle ->
            findNavController().navigate(ChooseMuscleFragmentDirections.toChooseExerciseFragment(muscle.name))
        }
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        vm.muscleList.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        
        activity?.fab?.hide()
    }
}
