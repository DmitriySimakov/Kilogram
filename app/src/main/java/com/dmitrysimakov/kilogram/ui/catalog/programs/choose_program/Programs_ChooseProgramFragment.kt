package com.dmitrysimakov.kilogram.ui.catalog.programs.choose_program

import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.ui.catalog.CatalogTabFragmentDirections.Companion.toChooseProgramDayFragment
import com.dmitrysimakov.kilogram.ui.catalog.CatalogTabFragmentDirections.Companion.toCreateProgramDialog
import com.dmitrysimakov.kilogram.ui.common.choose_program.ChooseProgramFragment
import com.dmitrysimakov.kilogram.util.navigate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_choose_program.*

class Programs_ChooseProgramFragment : ChooseProgramFragment() {
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        adapter.clickCallback = { navigate(toChooseProgramDayFragment(it._id)) }
    
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                vm.deleteProgram(adapter.getItem(viewHolder.adapterPosition)._id)
            }
        }).attachToRecyclerView(recyclerView)
    
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{ navigate(toCreateProgramDialog()) }
    }
}