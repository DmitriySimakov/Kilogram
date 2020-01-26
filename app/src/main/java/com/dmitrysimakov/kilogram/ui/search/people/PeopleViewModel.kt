package com.dmitrysimakov.kilogram.ui.search.people

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.remote.models.Subscriptions
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.data.remote.relation.UserWithSubscriptionStatus
import com.dmitrysimakov.kilogram.data.remote.relation.withSubscriptionStatus
import com.dmitrysimakov.kilogram.util.*
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData
import com.dmitrysimakov.kilogram.util.live_data.liveData

class PeopleViewModel : ViewModel() {
    
    private val _searchText = MutableLiveData<String?>(null)
    val searchText: LiveData<String?> = _searchText
    
    private val _currentUser = MutableLiveData<User?>()
    
    val subscriptions = _currentUser.switchMap {  user ->
        if (user == null) AbsentLiveData.create()
        else subscriptionsDocument.liveData { it.toObject(Subscriptions::class.java) }
    }
    
    private val _loadedPeople = usersCollection.liveData { it.toObject(User::class.java)!! }
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
    
    fun followByUser(user: UserWithSubscriptionStatus) {
        val currentUser = _currentUser.value!!
        val writeBatch = firestore.batch()
        
        // Add user to currentUser's followedIds list
        val followedIds = subscriptions.value!!.followedIds.toMutableList()
        followedIds.add(user.id)
        writeBatch.update(subscriptionsDocument, "followedIds", followedIds)
    
        val userSubsDoc = subscriptionsCollection.document(user.id)
        userSubsDoc.get().addOnSuccessListener { doc ->
            // Add currentUser to user's followersIds list
            val subs = doc.toObject(Subscriptions::class.java)!!
            val followersIds = subs.followersIds.toMutableList()
            followersIds.add(currentUser.id)
            writeBatch.update(userSubsDoc, "followersIds", followersIds)
        
            // Increase subscriptions count
            writeBatch.update(userDocument, "followedCount", currentUser.followedCount + 1)
            writeBatch.update(usersCollection.document(user.id), "followersCount", user.followersCount + 1)
        
            // COMMIT
            writeBatch.commit()
        }
    }
    
    fun unfollowFromUser(user: UserWithSubscriptionStatus) {
        val currentUser = _currentUser.value!!
        
        val writeBatch = firestore.batch()
        // Remove user from currentUser's followedIds list
        val followedIds = subscriptions.value!!.followedIds.toMutableList()
        followedIds.remove(user.id)
        writeBatch.update(subscriptionsDocument, "followedIds", followedIds)
    
        val userSubsDoc = subscriptionsCollection.document(user.id)
        userSubsDoc.get().addOnSuccessListener { doc ->
            // Remove currentUser from user's followersIds list
            val userSubs = doc.toObject(Subscriptions::class.java)!!
            val followersIds = userSubs.followersIds.toMutableList()
            followersIds.remove(currentUser.id)
            writeBatch.update(userSubsDoc, "followersIds", userSubs.followersIds)
        
            // Increase subscriptions count
            writeBatch.update(userDocument, "followedCount", currentUser.followedCount - 1)
            writeBatch.update(usersCollection.document(user.id), "followersCount", user.followersCount - 1)
        
            // COMMIT
            writeBatch.commit()
        }
    }
}