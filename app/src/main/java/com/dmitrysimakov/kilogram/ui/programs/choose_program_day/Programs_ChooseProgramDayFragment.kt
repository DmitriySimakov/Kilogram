package com.dmitrysimakov.kilogram.ui.programs.choose_program_day

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.ui.common.choose_program_day.ChooseProgramDayFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_choose_program_day.*
import timber.log.Timber
import java.util.*

class Programs_ChooseProgramDayFragment : ChooseProgramDayFragment() {
    
    private val args: Programs_ChooseProgramDayFragmentArgs by navArgs()
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        vm.start(args.programId)
        
        adapter.clickCallback = { programDay ->
            findNavController().navigate(Programs_ChooseProgramDayFragmentDirections
                    .toExercisesFragment(programDay._id))
        }
    
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val startPos = viewHolder.adapterPosition
                val targetPos = target.adapterPosition
                Timber.d("$startPos $targetPos")
                Collections.swap(vm.programDays.value!!, startPos, targetPos)
                adapter.notifyItemMoved(startPos, targetPos)
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                vm.deleteTrainingDay(adapter.getItem(viewHolder.adapterPosition)._id)
            }
        }).attachToRecyclerView(recyclerView)
    
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(Programs_ChooseProgramDayFragmentDirections
                    .toCreateProgramDayDialog(adapter.itemCount + 1, args.programId))
        }
    }
    
    override fun onPause() {
        vm.updateIndexNumbers()
        super.onPause()
    }
}