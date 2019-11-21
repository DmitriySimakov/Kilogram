package com.dmitrysimakov.kilogram.ui.subscriptions.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.remote.Person
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_people.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : Fragment() {
    
    private val vm: PeopleViewModel by viewModel()
    
    private val adapter by lazy { PeopleListAdapter({ navigateToChatWith(it) }) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        
        vm.people.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        
        activity?.fab?.hide()
    }
    
    private fun navigateToChatWith(person: Person) {
        vm.getChatWith(person) { chatId ->
            //findNavController().navigate(PeopleFragmentDirections.toMessagesFragment(chatId))
        }
    }
}
