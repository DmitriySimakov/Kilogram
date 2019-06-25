package com.dmitrysimakov.kilogram.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.FriendlyMessage
import com.dmitrysimakov.kilogram.util.getViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber
import javax.inject.Inject

class MainFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mainViewModel by lazy { getViewModel(activity!!, viewModelFactory) }
    
    private val firebaseDb = FirebaseDatabase.getInstance()
    private val messagesRef = firebaseDb.reference.child("messages")
    private var msgListener: ChildEventListener? = null
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        exerciseBtn.setOnClickListener { navController.navigate(MainFragmentDirections.toExercises()) }
        programsBtn.setOnClickListener { navController.navigate(MainFragmentDirections.toPrograms()) }
        trainingsBtn.setOnClickListener { navController.navigate(MainFragmentDirections.toTrainings()) }
        measurementsBtn.setOnClickListener { navController.navigate(MainFragmentDirections.toMeasurements()) }
        
        sendBtn.setOnClickListener {
            val msg = FriendlyMessage("${messageET.text}", mainViewModel.username.value ?: "")
            messagesRef.push().setValue(msg)
            messageET.setText("")
        }
    
        mainViewModel.username.observe(this, Observer { username ->
            if (username != null) {
                attachMsgListener()
                messageET.visibility = View.VISIBLE
                sendBtn.visibility = View.VISIBLE
            } else {
                detachMsgListener()
                messageET.visibility = View.GONE
                sendBtn.visibility = View.GONE
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.fab?.hide()
    }
    
    override fun onPause() {
        detachMsgListener()
        super.onPause()
    }
    
    private fun attachMsgListener() {
        msgListener = object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(data: DataSnapshot, s: String?) {}
            override fun onChildChanged(data: DataSnapshot, s: String?) {}
            override fun onChildAdded(data: DataSnapshot, s: String?) {
                val msg = data.getValue(FriendlyMessage::class.java)
                Timber.d(msg?.text)
            }
            override fun onChildRemoved(data: DataSnapshot) {}
        }
        messagesRef.addChildEventListener(msgListener!!)
    }
    
    private fun detachMsgListener() {
        msgListener?.let {
            messagesRef.removeEventListener(it)
            msgListener = null
        }
    }
}
