package com.dmitrysimakov.kilogram.ui.home.measurements.add_measurement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.dmitrysimakov.kilogram.data.local.dao.MeasurementParamDao
import com.dmitrysimakov.kilogram.data.local.entity.Measurement
import com.dmitrysimakov.kilogram.data.local.relation.NewMeasurement
import com.dmitrysimakov.kilogram.data.repository.MeasurementRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class AddMeasurementViewModel (
        private val measurementParamDao: MeasurementParamDao,
        private val repository: MeasurementRepository
) : ViewModel() {

    private val measurementParams = liveData { emit(measurementParamDao.params()) }
    
    val measurements = measurementParams.map { it.map { param -> NewMeasurement(param.name) } }
    
    fun addMeasurements() = CoroutineScope(Dispatchers.IO).launch {
        measurements.value?.forEach { measurement ->
            measurement.value?.let {
                repository.insert(Measurement(0, LocalDate.now(), measurement.param, it))
            }
        }
    }
}
