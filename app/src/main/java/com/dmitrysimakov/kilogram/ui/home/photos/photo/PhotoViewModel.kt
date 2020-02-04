package com.dmitrysimakov.kilogram.ui.home.photos.photo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.repository.PhotoRepository

class PhotoViewModel(private val repository: PhotoRepository) : ViewModel() {
    
    val photoUri = MutableLiveData<String>()
    
    val photo = photoUri.switchMap { liveData {
        emit(repository.photo(it))
    }}
}