package com.dmitrysimakov.kilogram.ui.measurements.add_measurement

import androidx.lifecycle.ViewModel
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository

class MeasurementsViewModel (private val repository: MeasurementRepository) : ViewModel() {

    val measurementList = repository.loadMeasurements()
}
