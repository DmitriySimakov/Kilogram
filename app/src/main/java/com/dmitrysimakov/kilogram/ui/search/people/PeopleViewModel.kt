package com.dmitrysimakov.kilogram.ui.search.people

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.usersCollection
import com.dmitrysimakov.kilogram.util.meetsQuery
import kotlinx.coroutines.tasks.await

class PeopleViewModel : ViewModel() {
    
    val searchText = MutableLiveData<String?>(null)
    
    val user = MutableLiveData<User?>()
    
    private val _people = liveData {
        emit(usersCollection.get().await().toObjects(User::class.java))
    }
    val people = MediatorLiveData<List<User>>()
    
    init {
        listOf(_people, user, searchText).forEach { people.addSource(it) { filterPeople() } }
    }
    
    private fun filterPeople() {
        val loadedPeople = _people.value ?: return
        val user = user.value
        val searchText = searchText.value
        
        people.value = loadedPeople.filter { person ->
            (user == null || person.id != user.id) && (searchText == null || person.name.meetsQuery(searchText))
        }
    }
}