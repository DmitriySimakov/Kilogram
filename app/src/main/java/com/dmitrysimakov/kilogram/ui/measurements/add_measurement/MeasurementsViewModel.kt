package com.dmitrysimakov.kilogram.ui.measurements.add_measurement

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.relation.MeasurementWithPreviousResults
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import kotlinx.coroutines.launch

class MeasurementsViewModel (private val repository: MeasurementRepository) : ViewModel() {

    val measurementList = liveData { emit(repository.loadMeasurements()) }
}
