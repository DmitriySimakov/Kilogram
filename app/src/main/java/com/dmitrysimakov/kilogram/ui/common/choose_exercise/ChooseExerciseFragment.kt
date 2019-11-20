package com.dmitrysimakov.kilogram.ui.common.choose_exercise

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.databinding.FragmentChooseExerciseBinding
import com.dmitrysimakov.kilogram.ui.common.ChipGroupFilterAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class ChooseExerciseFragment : Fragment() {

    protected val vm: ChooseExerciseViewModel by viewModel()

    protected lateinit var binding: FragmentChooseExerciseBinding
    
    protected val exerciseAdapter by lazy { ExerciseListAdapter(vm) }

    protected lateinit var exerciseTargetAdapter: ChipGroupFilterAdapter
    protected lateinit var equipmentAdapter: ChipGroupFilterAdapter
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = FragmentChooseExerciseBinding.inflate(inflater)
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        binding.recyclerView.adapter = exerciseAdapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        
        exerciseTargetAdapter = ChipGroupFilterAdapter(binding.targetsCG) { id, isChecked ->
            vm.setChecked(vm.exerciseTargetList, id, isChecked)
        }
        equipmentAdapter = ChipGroupFilterAdapter(binding.equipmentCG) { id, isChecked ->
            vm.setChecked(vm.equipmentList, id, isChecked)
        }
        
        vm.exerciseList.observe(viewLifecycleOwner, Observer { exerciseAdapter.submitList(it) })
        vm.exerciseTargetList.observe(viewLifecycleOwner, Observer { if (it != null) exerciseTargetAdapter.submitList(it) })
        vm.equipmentList.observe(viewLifecycleOwner, Observer { equipmentAdapter.submitList(it) })
        
        activity?.fab?.hide()
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_filter, menu)
        
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        
        vm.searchText.value?.let { query ->
            if (query.isNotEmpty()) searchView.setQuery(query, false)
            searchView.isIconified = false
        }
        
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean{
                vm.setSearchText(newText)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
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