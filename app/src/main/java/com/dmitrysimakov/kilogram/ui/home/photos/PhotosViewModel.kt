package com.dmitrysimakov.kilogram.ui.home.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dmitrysimakov.kilogram.data.repository.PhotoRepository

class PhotosViewModel(private val repository: PhotoRepository) : ViewModel() {
    val photos = repository.photos().asLiveData()
}