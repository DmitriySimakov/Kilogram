package com.dmitrysimakov.kilogram.ui.home.trainings.choose_program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.common.ProgramsAdapter
import com.dmitrysimakov.kilogram.ui.home.trainings.choose_program.ChooseProgramFragmentDirections.Companion.toChooseProgramDayFragment
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.setXNavIcon
import kotlinx.android.synthetic.main.fragment_choose_program.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseProgramFragment : Fragment() {
    
    private val vm: ChooseProgramViewModel by viewModel()
    
    private val adapter by lazy { ProgramsAdapter { navigate(toChooseProgramDayFragment(it.id)) } }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_program, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        setXNavIcon()
        
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        vm.programs.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}
