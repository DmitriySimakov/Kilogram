package com.dmitrysimakov.kilogram.ui.programs.programs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_programs.*
import javax.inject.Inject

class ProgramsFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    private lateinit var viewModel: ProgramsViewModel
    
    lateinit var adapter: ProgramsAdapter
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_programs, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        viewModel = getViewModel(viewModelFactory)
        adapter = ProgramsAdapter(executors) { program ->
//            findNavController().navigate(ProgramsFragmentDirections
//                    .toTrainingFragment(training._id, training.duration == null))
        }
        programs_rv.adapter = adapter
        viewModel.programList.observe(this, Observer { adapter.submitList(it) })
        
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val program = adapter.get(viewHolder.adapterPosition)
                viewModel.deleteProgram(program)
            }
        }).attachToRecyclerView(programs_rv)
        
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(ProgramsFragmentDirections.toCreateProgramDialog())
        }
    }
}
