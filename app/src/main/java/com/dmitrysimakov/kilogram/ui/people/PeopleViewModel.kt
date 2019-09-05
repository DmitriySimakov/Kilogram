package com.dmitrysimakov.kilogram.ui.people

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.remote.Chat
import com.dmitrysimakov.kilogram.data.remote.Person
import com.dmitrysimakov.kilogram.util.chatsCollection
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.user
import com.dmitrysimakov.kilogram.util.userDocument
import com.dmitrysimakov.kilogram.util.usersCollection
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class PeopleViewModel @Inject constructor() : ViewModel() {
    
    val people = Transformations.map(usersCollection.liveData { doc ->
        doc.toObject(Person::class.java)!!.also { person -> person.id = doc.id }
    }) { it.filter { person -> person.id != user!!.uid } }
    
    fun getChatWith(person: Person, callback: ((String) -> Unit)) {
        val curUserDirectsDocument = userDocument.collection("directChats").document("directChats")
        curUserDirectsDocument.get().addOnSuccessListener { doc ->
            val chatId = doc.get(person.id) as String?
            if (chatId == null) {
                chatsCollection.add(Chat(
                        listOf(
                                Chat.Member(user!!.uid, user!!.displayName
                                        ?: "", user!!.photoUrl.toString()),
                                Chat.Member(person.id, person.name, person.photoUrl)),
                        listOf(user!!.uid, person.id))
                ).addOnSuccessListener { chatDoc ->
                    curUserDirectsDocument.set(hashMapOf(person.id to chatDoc.id), SetOptions.merge())
                    callback(chatDoc.id)
                }
            } else {
                callback(chatId)
            }
        }
    }
}