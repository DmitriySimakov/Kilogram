package com.dmitrysimakov.kilogram.ui.search.people

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.remote.models.SubscriptionAction
import com.dmitrysimakov.kilogram.data.remote.models.Subscriptions
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.data.remote.relation.UserWithSubscriptionStatus
import com.dmitrysimakov.kilogram.data.remote.relation.withSubscriptionStatus
import com.dmitrysimakov.kilogram.util.*
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData
import com.dmitrysimakov.kilogram.util.live_data.liveData
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
    
    fun updateSubscriptions(user: UserWithSubscriptionStatus, action: SubscriptionAction) { viewModelScope.launch {
        val currentUser = _currentUser.value!!
        val currentUserFollowedIds = subscriptions.value!!.followedIds.toMutableList()
        
        val userSubsDoc = subscriptionsCollection.document(user.id)
        val userSubs = userSubsDoc.get().await().toObject(Subscriptions::class.java)!!
        val userFollowersIds = userSubs.followersIds.toMutableList()
        
        var currentUserFollowedCount = currentUser.followedCount
        var userFollowersCount = user.followersCount
        
        when (action) {
            SubscriptionAction.SUBSCRIBE -> {
                currentUserFollowedIds.add(user.id)
                userFollowersIds.add(currentUser.id)
                currentUserFollowedCount++
                userFollowersCount++
            }
            SubscriptionAction.UNSUBSCRIBE -> {
                currentUserFollowedIds.remove(user.id)
                userFollowersIds.remove(currentUser.id)
                currentUserFollowedCount--
                userFollowersCount--
            }
        }
        
        firestore.batch()
                .update(subscriptionsDocument, "followedIds", currentUserFollowedIds)
                .update(userSubsDoc, "followersIds", userFollowersIds)
                .update(userDocument, "followedCount", currentUserFollowedCount)
                .update(usersCollection.document(user.id), "followersCount", userFollowersCount)
                .commit()
    }}
}