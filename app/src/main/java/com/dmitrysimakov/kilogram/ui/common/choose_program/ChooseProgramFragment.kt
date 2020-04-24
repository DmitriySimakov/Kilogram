package com.dmitrysimakov.kilogram.ui.common.choose_program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.common.ProgramsAdapter
import com.dmitrysimakov.kilogram.ui.common.choose_program.ChooseProgramFragmentDirections.Companion.toChooseProgramDayFragment
import com.dmitrysimakov.kilogram.util.live_data.Event
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.popBackStack
import com.dmitrysimakov.kilogram.util.setXNavIcon
import kotlinx.android.synthetic.main.fragment_choose_program.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseProgramFragment : Fragment() {
    
    private val args: ChooseProgramFragmentArgs by navArgs()
    
    private val vm: ChooseProgramViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private val adapter by lazy { ProgramsAdapter { program ->
        when {
            args.publishProgram -> {
                vm.publishProgram(program)
                vm.programPublishedEvent.observe(viewLifecycleOwner) { popBackStack() }
            }
            args.toChooseProgramDay -> {
                navigate(toChooseProgramDayFragment(program.id))
            }
            else -> {
                sharedVM.program.value = Event(program)
                popBackStack()
            }
        }
    }}
    
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
