package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.MeasurementDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MeasurementRepository(
        private val measurementDao: MeasurementDao,
        private val io: CoroutineDispatcher = Dispatchers.IO
) {
    
    suspend fun loadMeasurements() = withContext(io) {
        measurementDao.getMeasurementWithPreviousResultList()
    }
}
