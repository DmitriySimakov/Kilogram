package com.dmitrysimakov.kilogram.ui.trainings.exercises

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
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise
import com.dmitrysimakov.kilogram.databinding.FragmentTrainingExercisesBinding
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.util.EventObserver
import kotlinx.android.synthetic.main.app_bar_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class TrainingExercisesFragment : Fragment() {
    
    private val args: TrainingExercisesFragmentArgs by navArgs()
    
    private val vm: TrainingExercisesViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private lateinit var binding: FragmentTrainingExercisesBinding
    
    private val exerciseRunningListAdapter by lazy {
        ExerciseRunningListAdapter(sharedVM.elapsedSessionTime, this, { toSetsFragment(it) }, { vm.finishExercise(it) }) }
    private val exercisePlannedListAdapter by lazy { ExercisePlannedListAdapter { toSetsFragment(it) } }
    private val exerciseFinishedListAdapter by lazy { ExerciseFinishedListAdapter { toSetsFragment(it) } }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrainingExercisesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.vm = vm
    
        setHasOptionsMenu(true)
        setupAdapters()
    
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(TrainingExercisesFragmentDirections
                    .toChooseExerciseFragment(exercisePlannedListAdapter.itemCount + 1 ,args.trainingId))
        }
        
        return binding.root
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm.setTrainingId(args.trainingId)
        setupNavigation()
        vm.training.observe(viewLifecycleOwner, Observer {  })
        vm.runningExercises.observe(viewLifecycleOwner, Observer { exerciseRunningListAdapter.submitList(it) })
        vm.plannedExercises.observe(viewLifecycleOwner, Observer { exercisePlannedListAdapter.submitList(it) })
        vm.finishedExercises.observe(viewLifecycleOwner, Observer { exerciseFinishedListAdapter.submitList(it) })
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
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { return false }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    vm!!.deleteExercise(exerciseFinishedListAdapter.getItem(viewHolder.adapterPosition))
                }
            }).attachToRecyclerView(finishedExercisesRV)
        }
    }
    
    private fun toSetsFragment(exercise: DetailedTrainingExercise) {
        findNavController().navigate(TrainingExercisesFragmentDirections
                .toTrainingSetsFragment(exercise._id))
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
    
    private fun setupNavigation() {
        vm.trainingFinishedEvent.observe(viewLifecycleOwner, EventObserver{
            findNavController().popBackStack()
        })
    }
}
