package com.dmitrysimakov.kilogram.ui.subscriptions.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.util.user
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_exercises.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatsFragment : Fragment() {
    
    private val vm: ChatsViewModel by viewModel()
    
    private val adapter by lazy { ChatsListAdapter(user!!.uid) {
        //findNavController().navigate(ChatsFragmentDirections.toMessagesFragment(it.id))
    }}
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        
        vm.chats.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        
        activity?.fab?.hide()
    }
}
