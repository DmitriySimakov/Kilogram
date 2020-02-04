package com.dmitrysimakov.kilogram.ui.search.people

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.ui.common.UserListAdapter
import com.dmitrysimakov.kilogram.ui.search.people.PeopleFragmentDirections.Companion.toPersonPageFragment
import com.dmitrysimakov.kilogram.util.navigate
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.android.synthetic.main.fragment_people.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : Fragment() {
    
    private val vm: PeopleViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private val adapter by lazy { UserListAdapter { navigate(toPersonPageFragment(it.id)) } }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_people, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        
        sharedVM.user.observe(viewLifecycleOwner) { vm.user.setNewValue(it) }
        vm.people.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        
        val searchView = menu.findItem(R.id.search).actionView as SearchView
    
        val query = vm.searchText.value
        if (query?.trim()?.isNotEmpty() == true) {
            searchView.setQuery(query, false)
            searchView.isIconified = false
        }
        
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?) : Boolean {
                vm.searchText.setNewValue(newText)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}
