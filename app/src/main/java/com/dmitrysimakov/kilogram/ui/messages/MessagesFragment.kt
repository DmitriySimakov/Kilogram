package com.dmitrysimakov.kilogram.ui.messages

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.Chat
import com.dmitrysimakov.kilogram.data.Message
import com.dmitrysimakov.kilogram.util.*
import com.google.firebase.firestore.Source
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_messages.*
import timber.log.Timber
import javax.inject.Inject

private const val RC_PHOTO_PICKER = 1

class MessagesFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    private val viewModel by lazy { getViewModel<MessagesViewModel>(viewModelFactory) }
    
    private val adapter by lazy { MessagesListAdapter(executors) }
    
    private val params by lazy { MessagesFragmentArgs.fromBundle(arguments!!) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
    
        viewModel.setChatId(params.id)
        viewModel.messages.observe(this, Observer { adapter.submitList(it) })
        
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
            viewModel.sendMessage(messageET.text.toString(), null)
            messageET.setText("")
        }
        
        activity?.fab?.hide()
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data?.data != null) {
            viewModel.sendImage(data.data!!)
        }
    }
}
