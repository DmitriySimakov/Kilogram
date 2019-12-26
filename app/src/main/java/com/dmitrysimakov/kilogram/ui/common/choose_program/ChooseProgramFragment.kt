package com.dmitrysimakov.kilogram.ui.common.choose_program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import kotlinx.android.synthetic.main.fragment_choose_program.*
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class ChooseProgramFragment : Fragment() {
    
    protected val vm: ChooseProgramViewModel by viewModel()
    
    protected val adapter by lazy { ChooseProgramAdapter() }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_program, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        vm.programs.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
}
