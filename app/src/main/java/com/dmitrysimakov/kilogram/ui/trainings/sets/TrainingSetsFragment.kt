package com.dmitrysimakov.kilogram.ui.trainings.sets

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.entity.TrainingExercise
import com.dmitrysimakov.kilogram.databinding.FragmentTrainingSetsBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_training_sets.*
import javax.inject.Inject

class TrainingSetsFragment : DaggerFragment() {
    
    @Inject lateinit var executors: AppExecutors
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { getViewModel<TrainingSetsViewModel>(viewModelFactory) }
    
    private val params by lazy { TrainingSetsFragmentArgs.fromBundle(arguments!!) }
    
    private lateinit var binding: FragmentTrainingSetsBinding
    
    private lateinit var adapter: TrainingSetsAdapter
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrainingSetsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        
        setHasOptionsMenu(true)
        
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.trainingId = params.trainingId
        viewModel.setTrainingExercise(params.trainingExerciseId)
        
        viewModel.exercise.observe(this, Observer { exercise ->
            binding.exerciseMeasures = exercise.measures
        
            adapter = TrainingSetsAdapter(executors, exercise.measures) { set ->
                findNavController().navigate(TrainingSetsFragmentDirections
                        .toAddSetDialog(params.trainingId, params.trainingExerciseId, set._id,
                                set.prev_weight ?: 0, set.prev_reps ?: 0, set.prev_time ?: 0, set.prev_distance ?: 0))
            }
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    
    
            viewModel.sets.observe(this, Observer { adapter.submitList(it) })
            activity?.toolbar?.menu?.findItem(R.id.finish_exercise)?.isVisible = exercise.state == TrainingExercise.RUNNING
            
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    val set = adapter.getItem(viewHolder.adapterPosition)
                    viewModel.deleteSet(set._id)
                }
            }).attachToRecyclerView(recyclerView)
        })
        
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            var weight = 0
            var reps = 0
            var time = 0
            var distance = 0
            val currLast = try {viewModel.currSets.value?.last()} catch (e: Exception) {null}
            val prevLast = try {viewModel.prevSets.value?.last()} catch (e: Exception) {null}
            if (currLast != null) {
                weight = currLast.weight ?: 0
                reps = currLast.reps ?: 0
                time = currLast.time ?: 0
                distance = currLast.distance ?: 0
            } else if (prevLast != null) {
                weight = prevLast.weight ?: 0
                reps = prevLast.reps ?: 0
                time = prevLast.time ?: 0
                distance = prevLast.distance ?: 0
            }
            findNavController().navigate(TrainingSetsFragmentDirections
                    .toAddSetDialog(params.trainingId, params.trainingExerciseId, 0, weight, reps, time, distance))
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.training_sets, menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.finish_exercise -> {
            viewModel.finishExercise(params.trainingExerciseId)
            findNavController().popBackStack()
            true
        }
        else -> false
    }
}