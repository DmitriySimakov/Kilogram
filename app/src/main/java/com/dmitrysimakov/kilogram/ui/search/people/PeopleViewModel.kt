package com.dmitrysimakov.kilogram.ui.search.people

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.util.live_data.liveData
import com.dmitrysimakov.kilogram.util.meetsQuery
import com.dmitrysimakov.kilogram.util.usersCollection

class PeopleViewModel : ViewModel() {
    
    val searchText = MutableLiveData<String?>(null)
    
    val user = MutableLiveData<User?>()
    
    private val _loadedPeople = usersCollection.liveData { it.toObject(User::class.java)!! }
    val people = MediatorLiveData<List<User>>()
    
    init {
        listOf(_loadedPeople, user, searchText).forEach { people.addSource(it) { filterPeople() } }
    }
    
    private fun filterPeople() {
        val loadedPeople = _loadedPeople.value
        val user = user.value
        val searchText = searchText.value
        
        if (loadedPeople == null || user == null) {
            people.value = emptyList()
            return
        }
        
        people.value = loadedPeople.filter { person ->
            person.id != user.id && (searchText == null || person.name.meetsQuery(searchText))
        }
    }
}