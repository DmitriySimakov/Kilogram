package com.dmitrysimakov.kilogram.ui.programs.program_days

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
import kotlinx.android.synthetic.main.fragment_program_days.*
import javax.inject.Inject

class ProgramDaysFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    private lateinit var viewModel: ProgramDaysViewModel
    
    lateinit var adapter: ProgramDaysAdapter
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_program_days, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        viewModel = getViewModel(viewModelFactory)
        val params = ProgramDaysFragmentArgs.fromBundle(arguments!!)
        viewModel.setParams(params.programId)
        
        adapter = ProgramDaysAdapter(executors) { program ->
            //            findNavController().navigate(ProgramsFragmentDirections
//                    .toTrainingFragment(training._id, training.duration == null))
        }
        days_rv.adapter = adapter
        viewModel.trainingDays.observe(this, Observer { adapter.submitList(it) })
        
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                viewModel.deleteTrainingDay(adapter.get(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(days_rv)
        
        activity?.fab?.show()
        activity?.fab?.setOnClickListener{
            findNavController().navigate(ProgramDaysFragmentDirections.toCreateProgramDayDialog(params.programId))
        }
    }
}
