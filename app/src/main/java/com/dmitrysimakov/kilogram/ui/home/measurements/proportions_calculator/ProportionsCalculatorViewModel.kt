package com.dmitrysimakov.kilogram.ui.home.measurements.proportions_calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository

class ProportionsCalculatorViewModel (private val repository: MeasurementRepository) : ViewModel() {
    
    val measurementsWithCoefficients = liveData { emit(repository.lastMeasurementsWithCoefficients()) }
    
    val calculatorItems = measurementsWithCoefficients.map { list -> list.map { item ->
        val chest = list.find { it.coefficient == 1.0 }!!
        item.recommendedValue = chest.value?.let { it * item.coefficient }
        item
    }}
    
}
