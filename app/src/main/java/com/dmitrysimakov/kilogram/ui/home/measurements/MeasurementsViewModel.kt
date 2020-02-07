package com.dmitrysimakov.kilogram.ui.home.measurements

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import java.util.*

class MeasurementsViewModel (private val repository: MeasurementRepository) : ViewModel() {
    
    val date = MutableLiveData<Date>()
    
    val measurements = date.switchMap {
        repository.measurementsWithPreviousResults(it).asLiveData()
    }
}