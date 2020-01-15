package com.dmitrysimakov.kilogram.ui.subscriptions.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.remote.Subscriptions
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.data.remote.toUser
import com.dmitrysimakov.kilogram.util.*
import com.dmitrysimakov.kilogram.util.live_data.liveData

class PeopleViewModel : ViewModel() {
    
    private val _searchText = MutableLiveData<String?>(null)
    val searchText: LiveData<String?> = _searchText
    
    private val _currentUser = MutableLiveData<User?>()
    
    private val _people = usersCollection.liveData { it.toUser() }
    val people = MediatorLiveData<List<User>>()
    
    init {
        listOf(_people, _currentUser, _searchText).forEach { people.addSource(it) { filterPeople() } }
    }
    
    private fun filterPeople() {
        val loadedPeople = _people.value
        val user = _currentUser.value
        val searchText = _searchText.value
        
        if (loadedPeople == null || user == null) {
            people.value = null
            return
        }
        
        people.value = loadedPeople.filter { person ->
            person.id != user.id && (searchText == null || person.name.meetsQuery(searchText))
        }
    }
    
    fun setUser(user: User?) { _currentUser.setNewValue(user) }
    
    fun setSearchText(text: String?) { _searchText.setNewValue(text) }
    
    fun followByUser(user: User) {
        val currentUser = _currentUser.value!!
        
        val writeBatch = firestore.batch()
        val curUserSubsDoc = subscriptionsDocument(currentUser.id)
        curUserSubsDoc.get().addOnSuccessListener { doc ->
            // Add user to currentUser's followedIds list
            if (doc.exists()) {
                val subs = doc.toObject(Subscriptions::class.java)!!
                subs.followedIds.add(user.id)
                writeBatch.update(curUserSubsDoc, "followedIds", subs.followedIds)
            } else {
                writeBatch.set(curUserSubsDoc, Subscriptions(followedIds = mutableListOf(user.id)))
            }
            
            val userSubsDoc = subscriptionsDocument(user.id)
            userSubsDoc.get().addOnSuccessListener {
                // Add currentUser to user's followersIds list
                if (doc.exists()) {
                    val subs = doc.toObject(Subscriptions::class.java)!!
                    subs.followersIds.add(user.id)
                    writeBatch.update(userSubsDoc, "followersIds", subs.followersIds)
                } else {
                    writeBatch.set(userSubsDoc, Subscriptions(followersIds = mutableListOf(currentUser.id)))
                }
    
                // COMMIT
                writeBatch.commit()
            }
        }
    }
}