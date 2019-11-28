package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTraining
import timber.log.Timber
import java.util.*

class TrainingRepository(private val dao: TrainingDao) {
    
    fun detailedTrainingsFlow() = dao.detailedTrainingsFlow()
    
    suspend fun detailedTrainingsForDay(calendar: Calendar) : List<DetailedTraining> {
        val c = calendar
        val h = c.get(Calendar.HOUR)
        val m = c.get(Calendar.MINUTE)
        val s = c.get(Calendar.SECOND)
        val ms = c.get(Calendar.MILLISECOND)
        Timber.d("DATETIME $h $m $s $ms")
        val dayStartTime = calendar.time.time
        val nextDayStartTime = dayStartTime + 1000 * 60 * 60 * 24
        return dao.detailedTrainingsForDay()
    }
    
    suspend fun training(id: Long) = dao.training(id)
    
    suspend fun insert(training: Training) = dao.insert(training)
    
    suspend fun update(training: Training) = dao.update(training)
    
    suspend fun delete(id: Long) = dao.delete(id)
}