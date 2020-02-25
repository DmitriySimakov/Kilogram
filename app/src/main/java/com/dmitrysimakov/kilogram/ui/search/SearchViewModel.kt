package com.dmitrysimakov.kilogram.ui.search

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.data_sources.PostSource
import com.dmitrysimakov.kilogram.data.remote.usersCollection
import kotlinx.coroutines.tasks.await

class SearchViewModel(postSrc: PostSource) : ViewModel() {
    
    val user = MutableLiveData<User?>()
    
    private val _people = liveData {
        emit(usersCollection.get().await().toObjects(User::class.java))
    }
    val people = MediatorLiveData<List<User>>()
    
    val posts = postSrc.postsLive()
    
    init {
        listOf(_people, user).forEach { people.addSource(it) { filterPeople() } }
    }
    
    private fun filterPeople() {
        val loadedPeople = _people.value ?: return
        val user = user.value
        
        people.value = loadedPeople.filter { person -> user == null || person.id != user.id }
    }
}