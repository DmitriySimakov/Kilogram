package com.dmitrysimakov.kilogram.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.usersCollection
import kotlinx.coroutines.tasks.await

class SearchViewModel : ViewModel() {
    
    val people = liveData {
        emit(usersCollection.limit(10).get().await().toObjects(User::class.java))
    }
}