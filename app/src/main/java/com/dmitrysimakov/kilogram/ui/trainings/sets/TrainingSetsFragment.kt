package com.dmitrysimakov.kilogram.ui.trainings.sets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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

    private lateinit var viewModel: TrainingSetsViewModel
    
    private lateinit var binding: FragmentTrainingSetsBinding
    
    private lateinit var adapter: TrainingSetsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTrainingSetsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel(viewModelFactory)
        val params = TrainingSetsFragmentArgs.fromBundle(arguments!!)
        viewModel.setTrainingExercise(params.trainingExerciseId)
        
        viewModel.exerciseMeasures.observe(this, Observer { measures ->
            binding.exerciseMeasures = measures
            
            adapter = TrainingSetsAdapter(executors, measures) { set ->
                findNavController().navigate(TrainingSetsFragmentDirections
                        .toAddSetDialog(set._id, params.trainingExerciseId))
            }
            recyclerView.adapter = adapter
    
            viewModel.sets.observe(this, Observer { adapter.submitList(it) })
            
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    val set = adapter.get(viewHolder.adapterPosition)
                    viewModel.deleteSet(set)
                }
            }).attachToRecyclerView(recyclerView)
        })

        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(TrainingSetsFragmentDirections
                    .toAddSetDialog(0, params.trainingExerciseId))
        }
    }
}