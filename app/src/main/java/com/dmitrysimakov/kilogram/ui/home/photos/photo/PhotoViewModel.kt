package com.dmitrysimakov.kilogram.ui.home.photos.photo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.repository.PhotoRepository
import com.dmitrysimakov.kilogram.util.setNewValue

class PhotoViewModel(private val repository: PhotoRepository) : ViewModel() {
    
    private val _photoUri = MutableLiveData<String>()
    
    val photo = _photoUri.switchMap { liveData {
        emit(repository.photo(it))
    }}
    
    fun setPhotoUri(uri: String) { _photoUri.setNewValue(uri) }
}