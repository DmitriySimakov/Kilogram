package com.dmitrysimakov.kilogram.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.local.entity.Photo
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import com.dmitrysimakov.kilogram.data.repository.PhotoRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import kotlinx.coroutines.launch

class HomeViewModel(
        private val trainingRepository: TrainingRepository,
        private val photoRepository: PhotoRepository,
        private val measurementRepository: MeasurementRepository
) : ViewModel() {
    
    val trainings = trainingRepository.detailedTrainingsFlow().asLiveData()
    
    val recentPhotos = photoRepository.recentPhotos(3).asLiveData()
    
    val recentMeasurements = measurementRepository.lastMeasurementsWithPreviousResults().asLiveData()
    
    fun addPhoto(photo: Photo) = viewModelScope.launch {
        photoRepository.insert(photo)
    }
}