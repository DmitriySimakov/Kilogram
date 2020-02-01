package com.dmitrysimakov.kilogram.ui.common.person_page

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.remote.models.User
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import com.dmitrysimakov.kilogram.data.repository.PhotoRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingRepository
import com.dmitrysimakov.kilogram.util.setNewValue

class PersonPageViewModel(
        private val trainingRepository: TrainingRepository,
        private val photoRepository: PhotoRepository,
        private val measurementRepository: MeasurementRepository
) : ViewModel() {
    
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    
    fun setUser(user: User) { _user.setNewValue(user) }
    
    val trainings = trainingRepository.detailedTrainingsFlow().asLiveData()

    val recentPhotos = photoRepository.recentPhotos(3).asLiveData()

    val recentMeasurements = measurementRepository.lastMeasurementsWithPreviousResults().asLiveData()
            .map { it.filter { measurement -> measurement.id != null } }

}