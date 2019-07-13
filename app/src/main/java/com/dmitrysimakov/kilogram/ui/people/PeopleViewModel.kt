package com.dmitrysimakov.kilogram.ui.people

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.Chat
import com.dmitrysimakov.kilogram.data.FirebaseDao
import com.dmitrysimakov.kilogram.data.Person
import com.dmitrysimakov.kilogram.util.*
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class PeopleViewModel @Inject constructor(private val firebase: FirebaseDao) : ViewModel() {
    
    private val user = firebase.user!!
    
    val people = Transformations.map(firebase.usersCollection.liveData { doc ->
        doc.toObject(Person::class.java)!!.also { person -> person.id = doc.id }
    }) { it.filter { person -> person.id != user.uid } }
    
    fun getChatWith(person: Person, callback: ((String) -> Unit)) {
        val curUserDirectsDocument = firebase.userDocument.collection("directChats").document("directChats")
        curUserDirectsDocument.get().addOnSuccessListener { doc ->
            val chatId = doc.get(person.id) as String?
            if (chatId == null) {
                firebase.chatsCollection.add(Chat(
                        listOf(
                                Chat.Member(user.uid, user.displayName ?: "", user.photoUrl.toString()),
                                Chat.Member(person.id, person.name, person.photoUrl)),
                        listOf(user.uid, person.id))
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