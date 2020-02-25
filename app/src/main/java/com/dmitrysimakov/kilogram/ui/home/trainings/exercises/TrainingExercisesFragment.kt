package com.dmitrysimakov.kilogram.ui.home.trainings.exercises

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentTrainingExercisesBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.home.trainings.exercises.TrainingExercisesFragmentDirections.Companion.toExercisesFragment
import com.dmitrysimakov.kilogram.ui.home.trainings.exercises.TrainingExercisesFragmentDirections.Companion.toTrainingSetsFragment
import com.dmitrysimakov.kilogram.util.live_data.EventObserver
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setNewValue
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TrainingExercisesFragment : Fragment() {
    
    private val args: TrainingExercisesFragmentArgs by navArgs()
    
    private val vm: TrainingExercisesViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private lateinit var binding: FragmentTrainingExercisesBinding
    
    private val runningExercisesAdapter by lazy { TrainingExercisesAdapter(
            { navigate(toTrainingSetsFragment(it.id)) },
            { vm.finishExercise(it) }
    )}
    private val plannedExercisesAdapter by lazy { TrainingExercisesAdapter({
        navigate(toTrainingSetsFragment(it.id))
    })}
    private val finishedExercisesAdapter by lazy { TrainingExercisesAdapter ({
        navigate(toTrainingSetsFragment(it.id))
    })}
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        
        binding = FragmentTrainingExercisesBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
    
        setupAdapters()
    
        binding.fab.setOnClickListener{
            val num = plannedExercisesAdapter.itemCount + 1
            navigate(toExercisesFragment(num, null, args.trainingId))
        }
        
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.trainingId.setNewValue(args.trainingId)
        
        vm.training.observe(viewLifecycleOwner) {}
        vm.runningExercises.observe(viewLifecycleOwner) { runningExercisesAdapter.submitList(it) }
        vm.plannedExercises.observe(viewLifecycleOwner) { plannedExercisesAdapter.submitList(it) }
        vm.finishedExercises.observe(viewLifecycleOwner) { finishedExercisesAdapter.submitList(it) }
        vm.trainingFinishedEvent.observe(viewLifecycleOwner, EventObserver {
            sharedVM.onTrainingSessionFinished()
            popBackStack()
        })
        vm.trainingDeletedEvent.observe(viewLifecycleOwner, EventObserver {
            sharedVM.onTrainingSessionFinished()
            popBackStack()
        })
    }
    
    private fun setupAdapters() {
        with(binding) {
            val divider = DividerItemDecoration(context, RecyclerView.VERTICAL)
            runningExercisesRV.addItemDecoration(divider)
            plannedExercisesRV.addItemDecoration(divider)
            finishedExercisesRV.addItemDecoration(divider)
            
            runningExercisesRV.adapter = runningExercisesAdapter
            plannedExercisesRV.adapter = plannedExercisesAdapter
            finishedExercisesRV.adapter = finishedExercisesAdapter
        
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    vm!!.deleteExercise(runningExercisesAdapter.getItem(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(runningExercisesRV)
        
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    val startPos = viewHolder.adapterPosition
                    val targetPos = target.adapterPosition
                    Collections.swap(vm!!.plannedExercises.value!!, startPos, targetPos)
                    plannedExercisesAdapter.notifyItemMoved(startPos, targetPos)
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    vm!!.deleteExercise(plannedExercisesAdapter.getItem(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(plannedExercisesRV)
        
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    vm!!.deleteExercise(finishedExercisesAdapter.getItem(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(finishedExercisesRV)
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.training_exercises, menu)
        if (!args.trainingIsRunning) menu.removeItem(R.id.finish_training)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.finish_training -> {
            vm.finishTraining(sharedVM.elapsedSessionTime.value ?: 0)
            true
        }
        R.id.delete_training -> {
            vm.deleteTraining()
            true
        }
        else -> false
    }
    
    override fun onPause() {
        vm.updateIndexNumbers()
        super.onPause()
    }
}
