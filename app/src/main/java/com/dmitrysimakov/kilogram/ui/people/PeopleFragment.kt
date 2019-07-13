package com.dmitrysimakov.kilogram.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dmitrysimakov.kilogram.R
import com.dmitrysimakov.kilogram.data.Chat
import com.dmitrysimakov.kilogram.data.Person
import com.dmitrysimakov.kilogram.util.*
import com.google.firebase.firestore.SetOptions
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_people.*
import javax.inject.Inject

class PeopleFragment : DaggerFragment() {
    
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    
    @Inject lateinit var executors: AppExecutors
    
    private val mainViewModel by lazy { getViewModel(activity!!, viewModelFactory) }
    
    private val adapter by lazy { PeopleListAdapter(executors, { navigateToChatWith(it) }) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        
        recyclerView.adapter = adapter
        
        usersCollection.addSnapshotListener{ snapshot, _ ->
            adapter.submitList(snapshot?.documents
                    ?.map { doc -> doc.toObject(Person::class.java)?.also { it.id = doc.id } }
                    ?.filter { it?.id != user!!.uid }
            )
        }
        
        activity?.fab?.hide()
    }
    
    private fun navigateToChatWith(person: Person) {
        val curUserDirectsDocument = userDocument.collection("directChats").document("directChats")
        curUserDirectsDocument.get().addOnSuccessListener { doc ->
            val chatId = doc.get(person.id) as String?
            if (chatId == null) {
                val me = mainViewModel.user.value!!
                chatsCollection.add(Chat(
                        listOf(
                                Chat.Member(me.uid, me.displayName ?: "", me.photoUrl.toString()),
                                Chat.Member(person.id, person.name, person.photoUrl)),
                        listOf(me.uid, person.id))
                ).addOnSuccessListener { chatDoc ->
                    curUserDirectsDocument.set(hashMapOf(person.id to chatDoc.id), SetOptions.merge())
                    findNavController().navigate(PeopleFragmentDirections.toMessagesFragment(chatDoc.id))
                }
            } else {
                findNavController().navigate(PeopleFragmentDirections.toMessagesFragment(chatId))
            }
        }
    }
}
