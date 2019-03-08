package com.dmitrysimakov.kilogram.ui.programs.choose_program_day

import android.os.Bundle
import android.util.Log.d
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.ui.common.choose_program_day.ChooseProgramDayFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_choose_program_day.*
import java.util.*
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import kotlin.collections.HashSet

class Programs_ChooseProgramDayFragment : ChooseProgramDayFragment() {
    
    private val TAG = this::class.java.simpleName
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        val params = Programs_ChooseProgramDayFragmentArgs.fromBundle(arguments!!)
        viewModel.setProgram(params.programId)
        
        adapter.setClickListener { programDay ->
            findNavController().navigate(Programs_ChooseProgramDayFragmentDirections
                    .toExercisesFragment(programDay._id))
        }
    
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val startPos = viewHolder.adapterPosition
                val targetPos = target.adapterPosition
                d(TAG, "$startPos $targetPos")
                Collections.swap(viewModel.trainingDays.value, startPos, targetPos)
                adapter.notifyItemMoved(startPos, targetPos)
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                viewModel.deleteTrainingDay(adapter.get(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)
    
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(Programs_ChooseProgramDayFragmentDirections
                    .toCreateProgramDayDialog(adapter.itemCount + 1, params.programId))
        }
    }
    
    override fun onPause() {
        super.onPause()
        val list = mutableListOf<ProgramDay>()
        for (i in 0 until adapter.itemCount) {
            list.add(adapter.get(i).apply { num = i + 1 })
        }
        viewModel.updateNums(list)
    }
}