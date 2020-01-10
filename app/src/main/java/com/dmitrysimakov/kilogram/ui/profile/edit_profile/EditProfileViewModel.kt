package com.dmitrysimakov.kilogram.ui.profile.edit_profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.remote.User
import com.dmitrysimakov.kilogram.util.profileImagesRef
import com.dmitrysimakov.kilogram.util.setNewValue
import com.dmitrysimakov.kilogram.util.userDocument

class EditProfileViewModel : ViewModel() {
    
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    
    fun setUser(user: User) { _user.setNewValue(user) }
    
    fun saveChanges() {
        userDocument.set(user.value!!)
    }
    
    fun saveImage(uri: Uri) {
        setUser(user.value!!.copy(photoUrl = null))
        val imageRef = profileImagesRef.child(uri.lastPathSegment!!)
        imageRef.putFile(uri).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { photoUri ->
                setUser(user.value!!.copy(photoUrl = photoUri.toString()))
            }
        }
    }
}