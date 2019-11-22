package com.dmitrysimakov.kilogram.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.Photo
import com.dmitrysimakov.kilogram.data.repository.PhotoRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val photoRepository: PhotoRepository) : ViewModel() {
    
    val recentPhotos = photoRepository.recentPhotos(3).asLiveData()
    
    fun addPhoto(photo: Photo) = viewModelScope.launch {
        photoRepository.insert(photo)
    }
}