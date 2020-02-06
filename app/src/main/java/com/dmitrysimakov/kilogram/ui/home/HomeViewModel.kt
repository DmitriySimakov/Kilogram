package com.dmitrysimakov.kilogram.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.dmitrysimakov.kilogram.data.model.Photo
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import com.dmitrysimakov.kilogram.data.repository.PhotoRepository
import com.dmitrysimakov.kilogram.data.repository.ProgramRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import kotlinx.coroutines.launch

class HomeViewModel(
        private val trainingRepo: TrainingRepository,
        private val photoRepo: PhotoRepository,
        private val measurementRepo: MeasurementRepository,
        private val programRepo: ProgramRepository
) : ViewModel() {
    
    val trainings = trainingRepo.detailedTrainingsFlow().asLiveData()
    
    val recentPhotos = photoRepo.recentPhotos(3).asLiveData()
    
    val recentMeasurements = measurementRepo.lastMeasurementsWithPreviousResults().asLiveData()
            .map { it.filter { measurement -> measurement.id != null } }
    
    val programs = programRepo.programsFlow().asLiveData()
    
    fun addPhoto(photo: Photo) = viewModelScope.launch {
        photoRepo.insert(photo)
    }
    
    fun deleteProgram(id: String) = viewModelScope.launch {
        programRepo.delete(id)
    }
}