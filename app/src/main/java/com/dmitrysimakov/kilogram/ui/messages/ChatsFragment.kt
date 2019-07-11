package com.dmitrysimakov.kilogram.ui.messages

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.Chat
import com.dmitrysimakov.kilogram.util.*
import com.google.firebase.firestore.Query
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_exercises.*
import timber.log.Timber
import javax.inject.Inject

class ChatsFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    private val adapter by lazy { ChatsListAdapter(executors, currentUserUid!!) { findNavController()
            .navigate(ChatsFragmentDirections.toMessagesFragment(it.id)) } }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        
        val chatsQuery = chatsCollection.whereArrayContains("membersIds", currentUserUid!!)
                .orderBy("lastMessage.timestamp", Query.Direction.DESCENDING)
        chatsQuery.addSnapshotListener{ snapshot, e ->
            if (e != null) {
                Timber.w(e, "Listen failed.")
                return@addSnapshotListener
            }
            snapshot?.let { adapter.submitList(snapshot.documents.map { doc ->
                doc.toObject(Chat::class.java)?.also { chat ->
                    chat.id = doc.id
                    val others = chat.members.filter { it.id != currentUserUid }
                    if (chat.name == null) {
                        chat.name = others.joinToString(", ") { it.name }
                    }
                    if (chat.members.size == 2) {
                        chat.photoUrl = others.first().photoUrl
                    }
                }
            })}
        }
        
        activity?.fab?.hide()
    }
}
