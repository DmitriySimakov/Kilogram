package com.dmitrysimakov.kilogram.ui.profile.edit_profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.util.imagesRef
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.userDocument
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EditProfileViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User>()
    
    val name = MutableLiveData<String>()
    val photoUrl = MutableLiveData<String>()
    
    fun setUser(user: User) {
        _user.setNewValue(user)
        name.setNewValue(user.name)
        photoUrl.setNewValue(user.photoUrl)
    }
    
    fun saveChanges() {
        val user = _user.value!!.copy(
                name = name.value!!,
                photoUrl = photoUrl.value
        )
        userDocument.set(user)
    }
    
    fun saveImage(uri: Uri) { viewModelScope.launch {
        photoUrl.setNewValue(null)
        val imageRef = imagesRef.child(uri.lastPathSegment!!)
        imageRef.putFile(uri).await()
        val photoUri = imageRef.downloadUrl.await().toString()
        photoUrl.setNewValue(photoUri)
    }}
}