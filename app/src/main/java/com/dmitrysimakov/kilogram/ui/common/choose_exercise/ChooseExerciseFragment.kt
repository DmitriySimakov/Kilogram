package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.os.Bundle
import android.view.*
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentChooseExerciseBinding
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject

abstract class ChooseExerciseFragment : DaggerFragment() {

    @Inject lateinit var executors: AppExecutors
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel by lazy { getViewModel<ChooseExerciseViewModel>(viewModelFactory) }

    protected lateinit var binding: FragmentChooseExerciseBinding
    
    protected val exerciseAdapter by lazy { ExerciseListAdapter(executors, viewModel) }

    protected lateinit var muscleAdapter: ChipGroupFilterAdapter
    protected lateinit var mechanicsTypeAdapter: ChipGroupFilterAdapter
    protected lateinit var exerciseTypeAdapter: ChipGroupFilterAdapter
    protected lateinit var equipmentAdapter: ChipGroupFilterAdapter
    
    protected var wasPopped = false
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = FragmentChooseExerciseBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        binding.recyclerView.adapter = exerciseAdapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        
        muscleAdapter = ChipGroupFilterAdapter(binding.musclesCG) { id, isChecked ->
            viewModel.setChecked(viewModel.muscleList, id, isChecked)
        }
        mechanicsTypeAdapter = ChipGroupFilterAdapter(binding.mechanicsTypeCG) { id, isChecked ->
            viewModel.setChecked(viewModel.mechanicsTypeList, id, isChecked)
        }
        exerciseTypeAdapter = ChipGroupFilterAdapter(binding.exerciseTypeCG) { id, isChecked ->
            viewModel.setChecked(viewModel.exerciseTypeList, id, isChecked)
        }
        equipmentAdapter = ChipGroupFilterAdapter(binding.equipmentCG) { id, isChecked ->
            viewModel.setChecked(viewModel.equipmentList, id, isChecked)
        }
        
        viewModel.exerciseList.observe(this, Observer { exerciseAdapter.submitList(it) })
        viewModel.muscleList.observe(this, Observer { muscleAdapter.submitList(it) })
        viewModel.mechanicsTypeList.observe(this, Observer { mechanicsTypeAdapter.submitList(it) })
        viewModel.exerciseTypeList.observe(this, Observer { exerciseTypeAdapter.submitList(it) })
        viewModel.equipmentList.observe(this, Observer { equipmentAdapter.submitList(it) })
        
        activity?.fab?.hide()
    }
    
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.sort_filter, menu)
    }
    
    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.sort -> {
            
            true
        }
        R.id.filter -> {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.END)
            }
            true
        }
        else -> false
    }
}