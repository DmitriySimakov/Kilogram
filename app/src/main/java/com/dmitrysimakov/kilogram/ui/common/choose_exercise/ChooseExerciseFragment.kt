package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentChooseExerciseBinding
import com.dmitrysimakov.kilogram.ui.common.ChipGroupFilterAdapter
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
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
        viewModel.muscleList.observe(this, Observer { if (it != null) muscleAdapter.submitList(it) })
        viewModel.mechanicsTypeList.observe(this, Observer { mechanicsTypeAdapter.submitList(it) })
        viewModel.exerciseTypeList.observe(this, Observer { exerciseTypeAdapter.submitList(it) })
        viewModel.equipmentList.observe(this, Observer { equipmentAdapter.submitList(it) })
        
        activity?.fab?.hide()
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_filter, menu)
        menu.let {
            val searchView = menu.findItem(R.id.search).actionView as SearchView
            
            viewModel.searchText.value?.let { query ->
                if (query.isNotEmpty()) searchView.setQuery(query, false)
                searchView.isIconified = false
            }
            
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean{
                    viewModel.setSearchText(newText)
                    return true
                }
            })
        }
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
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