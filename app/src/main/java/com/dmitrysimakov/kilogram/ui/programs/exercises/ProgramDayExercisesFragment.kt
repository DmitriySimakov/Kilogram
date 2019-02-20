package com.dmitrysimakov.kilogram.ui.programs.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.trainings.exercises.TrainingExerciseListAdapter
import com.dmitrysimakov.kilogram.ui.trainings.exercises.TrainingExercisesViewModel
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_exercises.*
import javax.inject.Inject

class ProgramDayExercisesFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    protected lateinit var viewModel: ProgramDayExercisesViewModel
    
    protected lateinit var adapter: ProgramDayExerciseListAdapter
    
    private lateinit var params: ProgramDayExercisesFragmentArgs
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        val params = ProgramDayExercisesFragmentArgs.fromBundle(arguments!!)
    
        adapter = ProgramDayExerciseListAdapter(executors)
        recyclerView.adapter = adapter
    
        viewModel = getViewModel(viewModelFactory)
        viewModel.setProgramDay(params.programDayId)
        viewModel.exercises.observe(this, Observer { adapter.submitList(it) })
    
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                viewModel.deleteExercise(adapter.get(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)
    
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(ProgramDayExercisesFragmentDirections.toChooseMuscleFragment(params.programDayId))
        }
    }
}