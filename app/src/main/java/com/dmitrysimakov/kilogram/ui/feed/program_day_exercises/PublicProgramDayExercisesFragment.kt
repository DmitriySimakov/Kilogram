package com.dmitrysimakov.kilogram.ui.feed.program_day_exercises

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
import com.dmitrysimakov.kilogram.ui.home.programs.exercises.ProgramDayExercisesAdapter
import com.dmitrysimakov.kilogram.util.setTitle
import kotlinx.android.synthetic.main.fragment_public_program_day_exercises.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PublicProgramDayExercisesFragment : Fragment() {
    
    private val args: PublicProgramDayExercisesFragmentArgs by navArgs()
    
    private val vm: PublicProgramDayExercisesViewModel by viewModel()
    
    private val adapter by lazy { ProgramDayExercisesAdapter() }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_public_program_day_exercises, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        
        vm.start(args.programId, args.programDayId)
        vm.programDay.observe(viewLifecycleOwner) { setTitle(it.name) }
        vm.exercises.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}
