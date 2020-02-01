package com.dmitrysimakov.kilogram.ui.common.person_page

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.usersCollection
import kotlinx.coroutines.tasks.await

class PersonPageViewModel : ViewModel() {
    
    private val _userId = MutableLiveData<String>()
    
    val user = _userId.switchMap { id -> liveData {
        emit(usersCollection.document(id).get().await().toObject(User::class.java)!!)
    }}
    
    fun setUserId(id: String) { _userId.setNewValue(id) }

}