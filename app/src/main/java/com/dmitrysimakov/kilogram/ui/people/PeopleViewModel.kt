package com.dmitrysimakov.kilogram.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.dmitrysimakov.kilogram.data.remote.Chat
import com.dmitrysimakov.kilogram.data.remote.Person
import com.dmitrysimakov.kilogram.util.chatsCollection
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.user
import com.dmitrysimakov.kilogram.util.userDocument
import com.dmitrysimakov.kilogram.util.usersCollection
import com.google.firebase.firestore.SetOptions

class PeopleViewModel : ViewModel() {
    
    val people = usersCollection.liveData { doc ->
        doc.toObject(Person::class.java)!!.also { person -> person.id = doc.id }
    }.map { it.filter { person -> person.id != user!!.uid } }
    
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