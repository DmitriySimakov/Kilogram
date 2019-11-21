package com.dmitrysimakov.kilogram.ui.catalog.exercises.exercise_targets

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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_exercise_targets.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExerciseTargetsFragment : Fragment() {
    
    protected val vm: ExerciseTargetsViewModel by viewModel()

    protected val adapter by lazy { MuscleListAdapter() }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_exercise_targets, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        adapter.clickCallback = { muscle ->
            findNavController().navigate(ExerciseTargetsFragmentDirections.toChooseExerciseFragment(muscle.name))
        }
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        vm.exerciseTargets.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        
        activity?.fab?.hide()
    }
}
