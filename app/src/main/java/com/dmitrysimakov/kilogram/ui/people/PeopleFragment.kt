package com.dmitrysimakov.kilogram.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.remote.Person
import com.dmitrysimakov.kilogram.util.*
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_people.*
import javax.inject.Inject

class PeopleFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    private val viewModel by lazy { getViewModel<PeopleViewModel>(viewModelFactory) }
    
    private val adapter by lazy { PeopleListAdapter(executors, { navigateToChatWith(it) }) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        
        viewModel.people.observe(this, Observer { adapter.submitList(it) })
        
        activity?.fab?.hide()
    }
    
    private fun navigateToChatWith(person: Person) {
        viewModel.getChatWith(person) { chatId ->
            findNavController().navigate(PeopleFragmentDirections.toMessagesFragment(chatId))
        }
    }
}
