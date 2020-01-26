package com.dmitrysimakov.kilogram.ui.home.trainings.training_sets

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.databinding.FragmentTrainingSetsBinding
import com.dmitrysimakov.kilogram.ui.home.trainings.training_sets.TrainingSetsFragmentDirections.Companion.toAddSetDialog
import com.dmitrysimakov.kilogram.util.live_data.EventObserver
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setTitle
import kotlinx.android.synthetic.main.fragment_training_sets.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrainingSetsFragment : Fragment() {
    
    private val args: TrainingSetsFragmentArgs by navArgs()
    
    private val vm: TrainingSetsViewModel by viewModel()
    
    private lateinit var binding: FragmentTrainingSetsBinding
    
    private val adapter by lazy { TrainingSetsAdapter { set ->
        val weight = set.weight ?: set.prevWeight ?: -1
        val reps = set.reps ?: set.prevReps ?: -1
        val time = set.time ?: set.prevTime ?: -1
        val distance = set.distance ?: set.prevDistance ?: -1
        
        navigate(toAddSetDialog(args.trainingExerciseId, set.id, weight, reps, time, distance))
    } }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrainingSetsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        
        setHasOptionsMenu(true)
        
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm.setTrainingExerciseId(args.trainingExerciseId)
        setupNavigation()
    
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    
        activity?.invalidateOptionsMenu()
    
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val set = adapter.getItem(viewHolder.adapterPosition)
                vm.deleteSet(set.id)
            }
        }).attachToRecyclerView(recyclerView)
        
        vm.trainingExercise.observe(viewLifecycleOwner)  { setTitle(it.exercise) }
        vm.exercise.observe(viewLifecycleOwner) {}
        vm.sets.observe(viewLifecycleOwner) { adapter.submitList(it) }
        
        binding.fab.setOnClickListener{
            val set = vm.currentSets.value?.lastOrNull() ?: vm.previousSets.value?.firstOrNull()
    
            val exercise = vm.exercise.value!!
            val weight = if (exercise.measuredInWeight) { set?.weight ?: 0 } else -1
            val reps = if (exercise.measuredInReps) { set?.reps ?: 0 } else -1
            val time = if (exercise.measuredInTime) { set?.time ?: 0 } else -1
            val distance = if (exercise.measuredInDistance) { set?.distance ?: 0 } else -1
            
            navigate(toAddSetDialog(args.trainingExerciseId, 0, weight, reps, time, distance))
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.training_sets, menu)
        val finishExerciseMenuItem = menu.findItem(R.id.finish_exercise)
        finishExerciseMenuItem?.isVisible = vm.trainingExercise.value?.state == TrainingExercise.RUNNING
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.finish_exercise -> {
            vm.finishExercise(args.trainingExerciseId)
            true
        }
        else -> false
    }
    
    private fun setupNavigation() {
        vm.trainingExerciseFinishedEvent.observe(viewLifecycleOwner, EventObserver { popBackStack() })
    }
}