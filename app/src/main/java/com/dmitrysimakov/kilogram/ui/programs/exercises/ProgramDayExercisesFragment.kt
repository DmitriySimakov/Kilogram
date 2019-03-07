package com.dmitrysimakov.kilogram.ui.programs.exercises

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.relation.ProgramExerciseR
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_exercises.*
import java.util.*
import javax.inject.Inject

class ProgramDayExercisesFragment : DaggerFragment() {
    
    private val TAG = this::class.java.simpleName
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    protected lateinit var viewModel: ProgramDayExercisesViewModel
    
    protected lateinit var adapter: ProgramDayExerciseListAdapter
    
    private val draggedItems = HashSet<ProgramExerciseR>()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        val params = ProgramDayExercisesFragmentArgs.fromBundle(arguments!!)
    
        adapter = ProgramDayExerciseListAdapter(executors)
        recyclerView.adapter = adapter
    
        viewModel = getViewModel(viewModelFactory)
        viewModel.setProgramDay(params.programDayId)
        viewModel.exercises.observe(this, Observer { adapter.submitList(it) })
    
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val startPos = viewHolder.adapterPosition
                val targetPos = target.adapterPosition
    
                d(TAG, "$startPos $targetPos")
                val startItem = adapter.get(startPos)
                val targetItem = adapter.get(targetPos)
    
                startItem.num = targetPos
                targetItem.num = startPos
    
                draggedItems.add(startItem)
                draggedItems.add(targetItem)
    
                Collections.swap(viewModel.exercises.value, startPos, targetPos)
                adapter.notifyItemMoved(startPos, targetPos)
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                viewModel.deleteExercise(adapter.get(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)
    
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(ProgramDayExercisesFragmentDirections
                    .toChooseMuscleFragment(adapter.itemCount + 1, params.programDayId))
        }
    }
    
    override fun onStop() {
        super.onStop()
        viewModel.swap(draggedItems)
    }
}