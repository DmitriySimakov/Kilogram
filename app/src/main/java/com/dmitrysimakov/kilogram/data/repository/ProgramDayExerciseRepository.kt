package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise

class ProgramDayExerciseRepository(private val dao: ProgramDayExerciseDao) {
   
    fun programDayExercisesFlow(programDayId: Long) = dao.programDayExercisesFlow(programDayId)
    
    suspend fun programDayExercises(programDayId: Long) = dao.programDayExercises(programDayId)
    
    suspend fun insert(programDayExercise: ProgramDayExercise) = dao.insert(programDayExercise)
    
    suspend fun delete(id: Long) = dao.delete(id)
    
    suspend fun update(programDayExercises: List<ProgramDayExercise>) = dao.update(programDayExercises)
}