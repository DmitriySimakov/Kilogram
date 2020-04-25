package com.dmitrysimakov.kilogram.ui.home.programs.exercises

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.home.programs.exercises.ProgramDayExercisesFragmentDirections.Companion.toExercisesFragment
import com.dmitrysimakov.kilogram.util.live_data.EventObserver
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.android.synthetic.main.fragment_program_day_exercises.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

class ProgramDayExercisesFragment : Fragment() {
    
    private val args: ProgramDayExercisesFragmentArgs by navArgs()
    
    private val vm: ProgramDayExercisesViewModel by viewModel()
    
    private val adapter by lazy { ProgramDayExercisesAdapter() }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_program_day_exercises, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        recyclerView.adapter = adapter
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
            navigate(toExercisesFragment(adapter.itemCount + 1, args.programDayId, null))
        }
    
        vm.programDayId.setNewValue(args.programDayId)
        vm.exercises.observe(viewLifecycleOwner) { adapter.submitList(it) }
        vm.programDayDeletedEvent.observe(viewLifecycleOwner, EventObserver { popBackStack() })
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.program_day_exercises, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.delete_program_day -> {
            vm.deleteProgramDay()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    
    override fun onPause() {
        vm.updateIndexNumbers()
        super.onPause()
    }
}