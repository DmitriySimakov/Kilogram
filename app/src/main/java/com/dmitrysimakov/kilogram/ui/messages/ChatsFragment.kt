package com.dmitrysimakov.kilogram.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.Chat
import com.dmitrysimakov.kilogram.util.AppExecutors
import com.dmitrysimakov.kilogram.util.chatsCollection
import com.dmitrysimakov.kilogram.util.user
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_exercises.*
import javax.inject.Inject

class ChatsFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    private val adapter by lazy { ChatsListAdapter(executors, user!!.uid) { findNavController()
            .navigate(ChatsFragmentDirections.toMessagesFragment(it.id)) } }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        
        val chatsQuery = chatsCollection.whereArrayContains("membersIds", user!!.uid)
        chatsQuery.addSnapshotListener{ snapshot, _ ->
            adapter.submitList(snapshot?.documents?.map { doc ->
                doc.toObject(Chat::class.java)?.also { chat ->
                    chat.id = doc.id
                    val others = chat.members.filter { it.id != user!!.uid }
                    if (chat.name == null) {
                        chat.name = others.joinToString(", ") { it.name }
                    }
                    if (chat.members.size == 2) {
                        chat.photoUrl = others.first().photoUrl
                    }
                }
            }?.sortedByDescending { it?.lastMessage?.timestamp })
        }
        
        activity?.fab?.hide()
    }
}
