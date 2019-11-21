package com.dmitrysimakov.kilogram.ui.home.measurements.add_measurement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository

class MeasurementsViewModel (private val repository: MeasurementRepository) : ViewModel() {

    val measurementList = liveData { emit(repository.measurementsWithPreviousResults()) }
}
