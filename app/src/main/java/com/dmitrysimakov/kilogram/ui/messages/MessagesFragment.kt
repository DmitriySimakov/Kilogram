package com.dmitrysimakov.kilogram.ui.messages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.FriendlyMessage
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_exercises.recyclerView
import kotlinx.android.synthetic.main.fragment_messages.*
import timber.log.Timber
import javax.inject.Inject

class MessagesFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    private val mainViewModel by lazy { getViewModel(activity!!, viewModelFactory) }
    
    private val adapter by lazy { MessagesListAdapter(executors) }
    
    private val messages = mutableListOf<FriendlyMessage>()
    private val firebaseDb = FirebaseDatabase.getInstance()
    private val messagesRef = firebaseDb.reference.child("messages")
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
    
        messagesRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(data: DataSnapshot, s: String?) {}
            override fun onChildChanged(data: DataSnapshot, s: String?) {}
            override fun onChildAdded(data: DataSnapshot, s: String?) {
                val msg = data.getValue(FriendlyMessage::class.java)
                msg?.let {
                    messages.add(it)
                    adapter.submitList(messages)
                }
            }
            override fun onChildRemoved(data: DataSnapshot) {}
        })
        
        messageET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                sendBtn.isEnabled = charSequence.isNotBlank()
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        
        sendBtn.setOnClickListener {
            val msg = FriendlyMessage("${messageET.text}", mainViewModel.username.value ?: "")
            messagesRef.push().setValue(msg)
            messageET.setText("")
        }
        
        activity?.fab?.hide()
    }
}
