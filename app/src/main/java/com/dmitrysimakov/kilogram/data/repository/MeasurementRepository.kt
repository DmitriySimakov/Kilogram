package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.MeasurementDao

class MeasurementRepository(private val measurementDao: MeasurementDao) {
    fun loadMeasurements() = measurementDao.getMeasurementWithPreviousResultList()
}
