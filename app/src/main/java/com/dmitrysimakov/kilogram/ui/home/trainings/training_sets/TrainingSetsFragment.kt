package com.dmitrysimakov.kilogram.ui.home.trainings.training_sets

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.databinding.FragmentTrainingSetsBinding
import com.dmitrysimakov.kilogram.util.EventObserver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_training_sets.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrainingSetsFragment : Fragment() {
    
    private val args: TrainingSetsFragmentArgs by navArgs()
    
    private val vm: TrainingSetsViewModel by viewModel()
    
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

        vm.setTrainingExerciseId(args.trainingExerciseId)
        setupNavigation()
    
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    
        activity?.invalidateOptionsMenu()
    
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val set = adapter.getItem(viewHolder.adapterPosition)
                vm.deleteSet(set._id)
            }
        }).attachToRecyclerView(recyclerView)
        
        vm.measures.observe(viewLifecycleOwner, Observer {
            binding.exerciseMeasures = it
    
            adapter = TrainingSetsAdapter(it) { set ->
                findNavController().navigate(TrainingSetsFragmentDirections.toAddSetDialog(
                        args.trainingExerciseId,
                        set._id,
                        set.prev_weight ?: 0,
                        set.prev_reps ?: 0,
                        set.prev_time ?: 0,
                        set.prev_distance ?: 0
                ))
            }
        })
        vm.sets.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            val set = vm.currentSets.value?.lastOrNull() ?: vm.previousSets.value?.firstOrNull()
            findNavController().navigate(TrainingSetsFragmentDirections.toAddSetDialog(
                    args.trainingExerciseId,
                    0,
                    set?.weight ?: 0,
                    set?.reps ?: 0,
                    set?.time ?: 0,
                    set?.distance ?: 0
            ))
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
        vm.trainingExerciseFinishedEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().popBackStack()
        })
    }
}