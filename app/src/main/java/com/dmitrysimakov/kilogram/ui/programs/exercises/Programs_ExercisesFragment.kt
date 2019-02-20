package com.dmitrysimakov.kilogram.ui.programs.exercises

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.ui.common.exercises.ExercisesFragment
import com.dmitrysimakov.kilogram.ui.common.exercises.TrainingExerciseListAdapter
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_exercises.*

class Programs_ExercisesFragment : ExercisesFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        val params = Programs_ExercisesFragmentArgs.fromBundle(arguments!!)
        viewModel.setProgramDay(params.programDayId)
    
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
//                viewModel.deleteExerciseFromProgramDay(adapter.get(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)
        
        activity?.fab?.setOnClickListener{
            findNavController().navigate(Programs_ExercisesFragmentDirections.toChooseMuscleFragment(params.programDayId))
        }
    }
}