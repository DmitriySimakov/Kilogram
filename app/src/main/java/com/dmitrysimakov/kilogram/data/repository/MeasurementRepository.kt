package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.MeasurementDao
import javax.inject.Inject

class MeasurementRepository @Inject constructor(private val measurementDao: MeasurementDao) {
    fun loadMeasurements() = measurementDao.getMeasurements()
}
