package com.dmitrysimakov.kilogram.ui.common.person_page

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.remote.models.SubscriptionAction
import com.dmitrysimakov.kilogram.data.remote.models.Subscriptions
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.util.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PersonPageViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User?>()
    private val _subscriptions = MutableLiveData<Subscriptions?>()
    val subscriptions: LiveData<Subscriptions?> = _subscriptions
    
    private val _personId = MutableLiveData<String>()
    val person = _personId.switchMap { id -> liveData {
        emit(usersCollection.document(id).get().await().toObject(User::class.java)!!)
    }}
    
    fun setUser(user: User?) { _user.setNewValue(user) }
    
    fun setSubscriptions(subscriptions: Subscriptions?) { _subscriptions.setNewValue(subscriptions) }
    
    fun setPersonId(id: String) { _personId.setNewValue(id) }
    
    
    fun updateSubscriptions(action: SubscriptionAction) { viewModelScope.launch {
        val currentUser = _user.value!!
        val currentUserFollowedIds = _subscriptions.value!!.followedIds.toMutableList()
        val person = person.value!!
        
        val userSubsDoc = subscriptionsCollection.document(person.id)
        val userSubs = userSubsDoc.get().await().toObject(Subscriptions::class.java)!!
        val userFollowersIds = userSubs.followersIds.toMutableList()
        
        var currentUserFollowedCount = currentUser.followedCount
        var userFollowersCount = person.followersCount
        
        when (action) {
            SubscriptionAction.SUBSCRIBE -> {
                currentUserFollowedIds.add(person.id)
                userFollowersIds.add(currentUser.id)
                currentUserFollowedCount++
                userFollowersCount++
            }
            SubscriptionAction.UNSUBSCRIBE -> {
                currentUserFollowedIds.remove(person.id)
                userFollowersIds.remove(currentUser.id)
                currentUserFollowedCount--
                userFollowersCount--
            }
        }
        
        firestore.batch()
                .update(subscriptionsDocument, "followedIds", currentUserFollowedIds)
                .update(userSubsDoc, "followersIds", userFollowersIds)
                .update(userDocument, "followedCount", currentUserFollowedCount)
                .update(usersCollection.document(person.id), "followersCount", userFollowersCount)
                .commit()
    }}
}