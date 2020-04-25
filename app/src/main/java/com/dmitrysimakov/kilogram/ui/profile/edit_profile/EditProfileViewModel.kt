package com.dmitrysimakov.kilogram.ui.profile.edit_profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.model.User
import com.dmitrysimakov.kilogram.data.remote.data_sources.FirebaseStorageSource
import com.dmitrysimakov.kilogram.data.remote.userDocument
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch

class EditProfileViewModel(private val firebaseStorage: FirebaseStorageSource) : ViewModel() {
    
    private val _user = MutableLiveData<User>()
    
    val name = MutableLiveData<String>()
    val photoUrl = MutableLiveData<String>()
    val gender = MutableLiveData("")
    val about = MutableLiveData("")
    val trainingTarget = MutableLiveData("")
    val gym = MutableLiveData("")
    
    fun setUser(user: User) {
        _user.setNewValue(user)
        name.setNewValue(user.name)
        photoUrl.setNewValue(user.photoUrl)
        gender.setNewValue(user.gender)
        about.setNewValue(user.about)
        trainingTarget.setNewValue(user.trainingTarget)
        gym.setNewValue(user.gym)
    }
    
    fun saveChanges() {
        val user = _user.value!!.copy(
                name = name.value!!,
                photoUrl = photoUrl.value,
                gender = gender.value!!,
                about = about.value!!,
                trainingTarget = trainingTarget.value!!,
                gym = gym.value!!
        )
        userDocument.set(user)
    }
    
    fun saveImage(uri: Uri) { viewModelScope.launch {
        photoUrl.setNewValue(null)
        val imageUri = firebaseStorage.uploadImage(uri)
        photoUrl.setNewValue(imageUri)
    }}
}