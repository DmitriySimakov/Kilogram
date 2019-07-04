package com.dmitrysimakov.kilogram.ui.messages

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.Message
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.getViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_exercises.recyclerView
import kotlinx.android.synthetic.main.fragment_messages.*
import javax.inject.Inject

class MessagesFragment : DaggerFragment() {
    
    val RC_PHOTO_PICKER = 2
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    private val mainViewModel by lazy { getViewModel(activity!!, viewModelFactory) }
    
    private val adapter by lazy { MessagesListAdapter(executors) }
    
    private val messages = mutableListOf<Message>()
    private val firebaseDb = FirebaseDatabase.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()
    private val messagesRef = firebaseDb.reference.child("messages")
    private val msgImagesRef = firebaseStorage.reference.child("message_images")
    
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
                val msg = data.getValue(Message::class.java)
                msg?.let {
                    messages.add(it)
                    adapter.submitList(messages)
                }
            }
            override fun onChildRemoved(data: DataSnapshot) {}
        })
        
        photoPickerBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            startActivityForResult(intent, RC_PHOTO_PICKER)
        }
        
        messageET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                sendBtn.isEnabled = charSequence.isNotBlank()
            }
            override fun afterTextChanged(editable: Editable) {}
        })
        
        sendBtn.setOnClickListener {
            val msg = Message(mainViewModel.user.value?.displayName ?: "", "${messageET.text}")
            messagesRef.push().setValue(msg)
            messageET.setText("")
        }
        
        activity?.fab?.hide()
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data?.data != null) {
            val uri = data.data!!
            val imageRef = msgImagesRef.child(uri.lastPathSegment!!)
            imageRef.putFile(uri).addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    messagesRef.push().setValue(
                            Message(mainViewModel.user.value?.displayName ?: "", "", downloadUri.toString()))
                }
            }
        }
    }
}
