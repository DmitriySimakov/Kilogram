package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise

class ProgramDayExerciseRepository(private val programDayExerciseDao: ProgramDayExerciseDao) {
   
    suspend fun programDayExercises(programDayId: Long) =
            programDayExerciseDao.programDayExercises(programDayId)
    
    suspend fun insert(programDayExercise: ProgramDayExercise) =
            programDayExerciseDao.insert(programDayExercise)
    
    suspend fun delete(id: Long) =
            programDayExerciseDao.delete(id)
    
    suspend fun update(programDayExercises: List<ProgramDayExercise>) =
            programDayExerciseDao.update(programDayExercises)
}