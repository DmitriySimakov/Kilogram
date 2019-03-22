package com.dmitrysimakov.kilogram.ui.trainings.exercises

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.databinding.FragmentTrainingExercisesBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*
import javax.inject.Inject

class TrainingExercisesFragment : DaggerFragment() {
    
    private val TAG = this::class.java.simpleName
    
    @Inject lateinit var executors: AppExecutors
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { getViewModel<TrainingExercisesViewModel>(viewModelFactory) }
    private val mainViewModel by lazy { getViewModel(activity!!, viewModelFactory) }
    
    private val params by lazy { TrainingExercisesFragmentArgs.fromBundle(arguments!!) }
    
    private lateinit var binding: FragmentTrainingExercisesBinding
    
    private val exerciseRunningListAdapter by lazy {
        ExerciseRunningListAdapter(mainViewModel.elapsedSessionTime, this, executors, { toSetsFragment(it) }, { viewModel.finishExercise(it) }) }
    private val exercisePlannedListAdapter by lazy { ExercisePlannedListAdapter(executors) { toSetsFragment(it) } }
    private val exerciseFinishedListAdapter by lazy { ExerciseFinishedListAdapter(executors) { toSetsFragment(it) } }
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel.setTraining(params.trainingId)
        viewModel.training.observe(this, Observer {  })
        viewModel.runningExercises.observe(this, Observer { exerciseRunningListAdapter.submitList(it) })
        viewModel.plannedExercises.observe(this, Observer { exercisePlannedListAdapter.submitList(it) })
        viewModel.finishedExercises.observe(this, Observer { exerciseFinishedListAdapter.submitList(it) })
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrainingExercisesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.vm = viewModel
    
        setHasOptionsMenu(true)
        setupAdapters()
    
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(TrainingExercisesFragmentDirections
                    .toChooseMuscleFragment(exercisePlannedListAdapter.itemCount + 1 ,params.trainingId))
        }
        
        return binding.root
    }
    
    private fun setupAdapters() {
        with(binding) {
            val divider = DividerItemDecoration(context, RecyclerView.VERTICAL)
            runningExercisesRV.addItemDecoration(divider)
            plannedExercisesRV.addItemDecoration(divider)
            finishedExercisesRV.addItemDecoration(divider)
            
            runningExercisesRV.adapter = exerciseRunningListAdapter
            plannedExercisesRV.adapter = exercisePlannedListAdapter
            finishedExercisesRV.adapter = exerciseFinishedListAdapter
        
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    viewModel.deleteExercise(exerciseRunningListAdapter.getItem(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(runningExercisesRV)
        
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    val startPos = viewHolder.adapterPosition
                    val targetPos = target.adapterPosition
                    Collections.swap(viewModel.plannedExercises.value, startPos, targetPos)
                    exercisePlannedListAdapter.notifyItemMoved(startPos, targetPos)
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    viewModel.deleteExercise(exercisePlannedListAdapter.getItem(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(plannedExercisesRV)
        
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    viewModel.deleteExercise(exerciseFinishedListAdapter.getItem(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(finishedExercisesRV)
        }
    }
    
    private fun toSetsFragment(exercise: TrainingExerciseR) {
        findNavController().navigate(TrainingExercisesFragmentDirections
                .toTrainingSetsFragment(exercise._id))
    }
    
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.training_exercises, menu)
        if (!params.trainingIsRunning) {
            menu?.removeItem(R.id.finish_training)
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.finish_training -> {
            viewModel.finishTraining(mainViewModel.elapsedSessionTime.value ?: 0)
            mainViewModel.onTrainingSessionFinished()
            findNavController().popBackStack()
            true
        }
        else -> false
    }
}
