package com.dmitrysimakov.kilogram.ui.messages

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.R
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_messages.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val RC_PHOTO_PICKER = 1

class MessagesFragment : Fragment() {
    
    private val vm: MessagesViewModel by viewModel()
    
    private val adapter by lazy { MessagesListAdapter() }
    
    private val args: MessagesFragmentArgs by navArgs()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
    
        vm.setChatId(args.id)
        vm.messages.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        
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
            vm.sendMessage(messageET.text.toString(), null)
            messageET.setText("")
        }
        
        activity?.fab?.hide()
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data?.data != null) {
            vm.sendImage(data.data!!)
        }
    }
}
