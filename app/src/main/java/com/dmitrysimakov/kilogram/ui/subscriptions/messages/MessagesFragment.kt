package com.dmitrysimakov.kilogram.ui.subscriptions.messages

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.ui.SharedViewModel
import com.dmitrysimakov.kilogram.util.dispatchGetImageContentIntent
import kotlinx.android.synthetic.main.fragment_messages.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val RC_PHOTO_PICKER = 1

class MessagesFragment : Fragment() {
    
    private val vm: MessagesViewModel by viewModel()
    private val sharedVM: SharedViewModel by sharedViewModel()
    
    private val adapter by lazy { MessagesListAdapter(vm) }
    
    private val args: MessagesFragmentArgs by navArgs()
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
    
        sharedVM.user.observe(viewLifecycleOwner) {user ->
            vm.start(user, args.id)
        }
        vm.messages.observe(viewLifecycleOwner) { adapter.submitList(it) }
        
        photoPickerBtn.setOnClickListener { dispatchGetImageContentIntent(RC_PHOTO_PICKER) }
        
        messageET.doOnTextChanged { text, _, _, _ ->
            sendBtn.isEnabled = text?.isNotBlank() ?: false
        }
        
        sendBtn.setOnClickListener {
            vm.sendMessage(messageET.text.toString(), null)
            messageET.setText("")
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK && data?.data != null) {
            vm.sendImage(data.data!!)
        }
    }
}
