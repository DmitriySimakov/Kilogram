package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.MainViewModel
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_exercises.*
import javax.inject.Inject

abstract class TrainingExercisesFragment : DaggerFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var executors: AppExecutors

    protected lateinit var viewModel: TrainingExercisesViewModel

    protected lateinit var adapter: TrainingExerciseListAdapter
    
    private lateinit var params: TrainingExercisesFragmentArgs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        setHasOptionsMenu(true)
        params = TrainingExercisesFragmentArgs.fromBundle(arguments!!)
    
        adapter = TrainingExerciseListAdapter(executors) {
            findNavController().navigate(TrainingExercisesFragmentDirections
                    .toTrainingSetsFragment(it.exercise_id, it._id))
        }
        recyclerView.adapter = adapter
    
        viewModel = getViewModel(viewModelFactory)
        viewModel.setTraining(params.trainingId)
        viewModel.training.observe(this, Observer {  })
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
            findNavController().navigate(TrainingExercisesFragmentDirections.toChooseMuscleFragment(params.trainingId))
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
