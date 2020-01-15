package com.dmitrysimakov.kilogram.ui.subscriptions.people

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.remote.Subscriptions
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.data.remote.relation.UserWithSubscriptionStatus
import com.dmitrysimakov.kilogram.data.remote.relation.withSubscriptionStatus
import com.dmitrysimakov.kilogram.data.remote.toUser
import com.dmitrysimakov.kilogram.util.*
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData
import com.dmitrysimakov.kilogram.util.live_data.liveData

class PeopleViewModel : ViewModel() {
    
    private val _searchText = MutableLiveData<String?>(null)
    val searchText: LiveData<String?> = _searchText
    
    private val _currentUser = MutableLiveData<User?>()
    
    val subscriptions = _currentUser.switchMap {  user ->
        if (user == null) AbsentLiveData.create()
        else subscriptionsDocument(user.id).liveData { it.toObject(Subscriptions::class.java)!! }
    }
    
    private val _loadedPeople = usersCollection.liveData { it.toUser() }
    private val _people = MediatorLiveData<List<UserWithSubscriptionStatus>>()
    val people: LiveData<List<UserWithSubscriptionStatus>> = _people
    
    init {
        listOf(_loadedPeople, _currentUser, _searchText, subscriptions).forEach { _people.addSource(it) { filterPeople() } }
    }
    
    private fun filterPeople() {
        val loadedPeople = _loadedPeople.value
        val user = _currentUser.value
        val searchText = _searchText.value
        
        if (loadedPeople == null || user == null) {
            _people.value = emptyList()
            return
        }
        
        _people.value = loadedPeople.filter { person ->
            person.id != user.id && (searchText == null || person.name.meetsQuery(searchText))
        }.map { it.withSubscriptionStatus(subscriptions.value) }
    }
    
    fun setUser(user: User?) { _currentUser.setNewValue(user) }
    
    fun setSearchText(text: String?) { _searchText.setNewValue(text) }
    
    fun followByUser(userId: String) {
        val currentUser = _currentUser.value!!
        
        val writeBatch = firestore.batch()
        val curUserSubsDoc = subscriptionsDocument(currentUser.id)
        curUserSubsDoc.get().addOnSuccessListener { doc ->
            // Add user to currentUser's followedIds list
            if (doc.exists()) {
                val subs = doc.toObject(Subscriptions::class.java)!!
                subs.followedIds.add(userId)
                writeBatch.update(curUserSubsDoc, "followedIds", subs.followedIds)
            } else {
                writeBatch.set(curUserSubsDoc, Subscriptions(followedIds = mutableListOf(userId)))
            }
            
            val userSubsDoc = subscriptionsDocument(userId)
            userSubsDoc.get().addOnSuccessListener {
                // Add currentUser to user's followersIds list
                if (doc.exists()) {
                    val subs = doc.toObject(Subscriptions::class.java)!!
                    subs.followersIds.add(currentUser.id)
                    writeBatch.update(userSubsDoc, "followersIds", subs.followersIds)
                } else {
                    writeBatch.set(userSubsDoc, Subscriptions(followersIds = mutableListOf(currentUser.id)))
                }
    
                // COMMIT
                writeBatch.commit()
            }
        }
    }
    
    fun unfollowFromUser(userId: String) {
        val currentUser = _currentUser.value!!
        
        val writeBatch = firestore.batch()
        val curUserSubsDoc = subscriptionsDocument(currentUser.id)
        curUserSubsDoc.get().addOnSuccessListener { doc ->
            // Remove user from currentUser's followedIds list
            val curUserSubs = doc.toObject(Subscriptions::class.java)!!
            curUserSubs.followedIds.remove(userId)
            writeBatch.update(curUserSubsDoc, "followedIds", curUserSubs.followedIds)
            
            val userSubsDoc = subscriptionsDocument(userId)
            userSubsDoc.get().addOnSuccessListener {
                // Remove currentUser from user's followersIds list
                val userSubs = doc.toObject(Subscriptions::class.java)!!
                userSubs.followersIds.remove(currentUser.id)
                writeBatch.update(userSubsDoc, "followersIds", userSubs.followersIds)
                
                // COMMIT
                writeBatch.commit()
            }
        }
    }
}