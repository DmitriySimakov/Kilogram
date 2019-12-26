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
import com.dmitrysimakov.kilogram.ui.home.trainings.exercises.TrainingExercisesFragmentDirections.Companion.toChooseExerciseFragment
import com.dmitrysimakov.kilogram.ui.home.trainings.exercises.TrainingExercisesFragmentDirections.Companion.toTrainingSetsFragment
import com.dmitrysimakov.kilogram.util.EventObserver
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.popBackStack
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TrainingExercisesFragment : Fragment() {
    
    private val args: TrainingExercisesFragmentArgs by navArgs()
    
    private val vm: TrainingExercisesViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private lateinit var binding: FragmentTrainingExercisesBinding
    
    private val exerciseRunningListAdapter by lazy { TrainingExercisesAdapter(
            { navigate(toTrainingSetsFragment(it._id)) },
            { vm.finishExercise(it) }
    )}
    private val exercisePlannedListAdapter by lazy { TrainingExercisesAdapter({
        navigate(toTrainingSetsFragment(it._id))
    })}
    private val exerciseFinishedListAdapter by lazy { TrainingExercisesAdapter ({
        navigate(toTrainingSetsFragment(it._id))
    }) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrainingExercisesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.vm = vm
    
        setHasOptionsMenu(true)
        setupAdapters()
    
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            navigate(toChooseExerciseFragment(exercisePlannedListAdapter.itemCount + 1 ,args.trainingId))
        }
        
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.setTrainingId(args.trainingId)
        vm.trainingFinishedEvent.observe(viewLifecycleOwner, EventObserver{ popBackStack() })
        vm.training.observe(viewLifecycleOwner) { }
        vm.runningExercises.observe(viewLifecycleOwner) { exerciseRunningListAdapter.submitList(it) }
        vm.plannedExercises.observe(viewLifecycleOwner) { exercisePlannedListAdapter.submitList(it) }
        vm.finishedExercises.observe(viewLifecycleOwner) { exerciseFinishedListAdapter.submitList(it) }
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
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    vm!!.deleteExercise(exerciseRunningListAdapter.getItem(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(runningExercisesRV)
        
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    val startPos = viewHolder.adapterPosition
                    val targetPos = target.adapterPosition
                    Collections.swap(vm!!.plannedExercises.value!!, startPos, targetPos)
                    exercisePlannedListAdapter.notifyItemMoved(startPos, targetPos)
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    vm!!.deleteExercise(exercisePlannedListAdapter.getItem(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(plannedExercisesRV)
        
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    vm!!.deleteExercise(exerciseFinishedListAdapter.getItem(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(finishedExercisesRV)
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.training_exercises, menu)
        if (!args.trainingIsRunning) {
            menu.removeItem(R.id.finish_training)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.finish_training -> {
            vm.finishTraining(sharedVM.elapsedSessionTime.value ?: 0)
            sharedVM.onTrainingSessionFinished()
            true
        }
        else -> false
    }
    
    override fun onPause() {
        vm.updateIndexNumbers()
        super.onPause()
    }
}
