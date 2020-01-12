package com.dmitrysimakov.kilogram.ui.subscriptions.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.data.remote.toUser
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.meetsQuery
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.usersCollection

class PeopleViewModel : ViewModel() {
    
    private val _searchText = MutableLiveData<String?>(null)
    val searchText: LiveData<String?> = _searchText
    
    private val _user = MutableLiveData<User?>()
    
    private val _people = usersCollection.liveData { it.toUser() }
    val people = MediatorLiveData<List<User>>()
    
    init {
        listOf(_people, _user, _searchText).forEach { people.addSource(it) { filterPeople() } }
    }
    
    private fun filterPeople() {
        val loadedPeople = _people.value
        val user = _user.value
        val searchText = _searchText.value
        
        if (loadedPeople == null || user == null) {
            people.value = null
            return
        }
        
        people.value = loadedPeople.filter { person ->
            person.id != user.id && (searchText == null || person.name.meetsQuery(searchText))
        }
    }
    
    fun setUser(user: User?) { _user.setNewValue(user) }
    
    fun setSearchText(text: String?) { _searchText.setNewValue(text) }
}