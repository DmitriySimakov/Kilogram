package com.dmitrysimakov.kilogram.ui.programs.exercises

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
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
    
    private val viewModel by lazy { getViewModel<ProgramDayExercisesViewModel>(viewModelFactory) }
    
    private val adapter by lazy { ProgramDayExerciseListAdapter(executors) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercises, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        val params = ProgramDayExercisesFragmentArgs.fromBundle(arguments!!)
    
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    
        viewModel.setProgramDay(params.programDayId)
        viewModel.exercises.observe(this, Observer { adapter.submitList(it) })
    
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val startPos = viewHolder.adapterPosition
                val targetPos = target.adapterPosition
                d(TAG, "$startPos $targetPos")
                Collections.swap(viewModel.exercises.value, startPos, targetPos)
                adapter.notifyItemMoved(startPos, targetPos)
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                viewModel.deleteExercise(adapter.getItem(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)
    
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(ProgramDayExercisesFragmentDirections
                    .toChooseExerciseFragment(adapter.itemCount + 1, params.programDayId))
        }
    }
    
    override fun onPause() {
        super.onPause()
        val list = mutableListOf<ProgramExerciseR>()
        for (i in 0 until adapter.itemCount) {
            list.add(adapter.getItem(i).apply { num = i + 1 })
        }
        viewModel.updateNums(list)
    }
}