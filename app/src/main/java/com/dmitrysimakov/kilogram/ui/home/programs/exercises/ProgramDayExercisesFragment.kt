package com.dmitrysimakov.kilogram.ui.home.programs.exercises

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.navigate
import kotlinx.android.synthetic.main.fragment_program_day_exercises.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

class ProgramDayExercisesFragment : Fragment() {
    
    private val args: ProgramDayExercisesFragmentArgs by navArgs()
    
    private val vm: ProgramDayExercisesViewModel by viewModel()
    
    private val adapter by lazy { ProgramDayExerciseListAdapter() }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_program_day_exercises, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
    
        vm.setProgramDayId(args.programDayId)
        vm.exercises.observe(viewLifecycleOwner) { adapter.submitList(it) }
    
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val startPos = viewHolder.adapterPosition
                val targetPos = target.adapterPosition
                Timber.d("$startPos $targetPos")
                Collections.swap(vm.exercises.value!!, startPos, targetPos)
                adapter.notifyItemMoved(startPos, targetPos)
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                vm.deleteExercise(adapter.getItem(viewHolder.adapterPosition).id)
            }
        }).attachToRecyclerView(recyclerView)
    
        fab.setOnClickListener{
            navigate(ProgramDayExercisesFragmentDirections.toExercisesFragment(adapter.itemCount + 1, args.programDayId, -1))
        }
    }
    
    override fun onPause() {
        vm.updateIndexNumbers()
        super.onPause()
    }
}