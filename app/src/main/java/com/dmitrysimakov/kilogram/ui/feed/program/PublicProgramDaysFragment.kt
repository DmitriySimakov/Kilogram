package com.dmitrysimakov.kilogram.ui.feed.program

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
import com.dmitrysimakov.kilogram.ui.home.trainings.choose_program_day.ChooseProgramDayAdapter
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.setTitle
import kotlinx.android.synthetic.main.fragment_public_program_days.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PublicProgramDaysFragment : Fragment() {
    
    private val args: PublicProgramDaysFragmentArgs by navArgs()
    
    private val vm: PublicProgramDaysViewModel by viewModel()
    
    private val adapter by lazy { ChooseProgramDayAdapter {} }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_public_program_days, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        
        vm.programId.setNewValue(args.programId)
        vm.program.observe(viewLifecycleOwner) { setTitle(it.name) }
        vm.programDays.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}
