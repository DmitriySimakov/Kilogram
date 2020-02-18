package com.dmitrysimakov.kilogram.ui.home.programs.program_days

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.home.programs.program_days.ProgramDaysFragmentDirections.Companion.toCreateProgramDayDialog
import com.dmitrysimakov.kilogram.ui.home.programs.program_days.ProgramDaysFragmentDirections.Companion.toProgramDayExercisesFragment
import com.dmitrysimakov.kilogram.util.live_data.EventObserver
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.android.synthetic.main.fragment_program_days.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ProgramDaysFragment : Fragment() {
    
    private val args: ProgramDaysFragmentArgs by navArgs()
    
    private val vm: ProgramDaysViewModel by viewModel()
    
    private val adapter by lazy { ProgramDaysAdapter { programDay ->
        navigate(toProgramDayExercisesFragment(programDay.id))
    }}
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_program_days, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val startPos = viewHolder.adapterPosition
                val targetPos = target.adapterPosition
                Collections.swap(vm.programDays.value!!, startPos, targetPos)
                adapter.notifyItemMoved(startPos, targetPos)
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                vm.deleteProgramDay(adapter.getItem(viewHolder.adapterPosition).id)
            }
        }).attachToRecyclerView(recyclerView)
    
        fab.setOnClickListener{
            navigate(toCreateProgramDayDialog(adapter.itemCount + 1, args.programId))
        }
        
        vm.programId.setNewValue(args.programId)
        vm.programDays.observe(viewLifecycleOwner) { adapter.submitList(it) }
        vm.programDeletedEvent.observe(viewLifecycleOwner, EventObserver { popBackStack() })
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.program_days, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.delete_program -> {
            vm.deleteProgram()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
    
    override fun onPause() {
        vm.updateIndexNumbers()
        super.onPause()
    }
}