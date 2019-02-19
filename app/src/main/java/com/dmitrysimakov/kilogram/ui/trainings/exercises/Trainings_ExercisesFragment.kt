package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.MainViewModel
import com.dmitrysimakov.kilogram.ui.common.exercises.ExercisesFragment
import com.dmitrysimakov.kilogram.ui.common.exercises.TrainingExerciseListAdapter
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_exercises.*

class Trainings_ExercisesFragment : ExercisesFragment() {
    
    private lateinit var params: Trainings_ExercisesFragmentArgs
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        setHasOptionsMenu(true)
        adapter.setClickListener {
            findNavController().navigate(Trainings_ExercisesFragmentDirections
                    .toTrainingSetsFragment(it.exercise_id, it._id))
        }
        
        params = Trainings_ExercisesFragmentArgs.fromBundle(arguments!!)
        viewModel.setTraining(params.trainingId)
        viewModel.training.observe(this, Observer {  })
        
        activity?.fab?.setOnClickListener{
            findNavController().navigate(Trainings_ExercisesFragmentDirections.toChooseMuscleFragment(params.trainingId))
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        if (params.trainingIsRunning) {
            inflater?.inflate(R.menu.active_training, menu);
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.finish_session -> {
                viewModel.finishSession()
                val mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
                mainViewModel.onTrainingSessionFinished()
                findNavController().popBackStack()
                return true
            }
        }
        return false
    }
}