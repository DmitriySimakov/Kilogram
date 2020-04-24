package com.dmitrysimakov.kilogram.ui.search.public_programs

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
import com.dmitrysimakov.kilogram.ui.search.public_programs.PublicProgramsFragmentDirections.Companion.toChooseProgramFragment
import com.dmitrysimakov.kilogram.ui.search.public_programs.PublicProgramsFragmentDirections.Companion.toPublicProgramDaysFragment
import com.dmitrysimakov.kilogram.util.navigate
import kotlinx.android.synthetic.main.fragment_choose_program.recyclerView
import kotlinx.android.synthetic.main.fragment_public_programs.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PublicProgramsFragment : Fragment() {
    
    private val vm: PublicProgramsViewModel by viewModel()
    
    private val adapter by lazy { ProgramsAdapter { program ->
        navigate(toPublicProgramDaysFragment(program.id))
    }}
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_public_programs, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        vm.programs.observe(viewLifecycleOwner) { adapter.submitList(it) }
        fab.setOnClickListener { navigate(toChooseProgramFragment()) }
    }
}
