package com.dmitrysimakov.kilogram.ui.measurements.addMeasurement

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import javax.inject.Inject

class MeasurementsViewModel @Inject constructor(private val repository: MeasurementRepository) : ViewModel() {

    val measurementList = repository.loadMeasurements()
}
