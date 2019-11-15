package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise

class ProgramDayExerciseRepository(private val programDayExerciseDao: ProgramDayExerciseDao) {
   
    suspend fun loadProgramDayExerciseList(programDayId: Long) =
            programDayExerciseDao.getProgramDayExerciseList(programDayId)
    
    suspend fun addExerciseToProgramDay(exercise: ProgramDayExercise) {
        programDayExerciseDao.insert(exercise)
    }
    
    suspend fun deleteExerciseFromProgramDay(exercise: ProgramDayExercise) {
        programDayExerciseDao.delete(exercise)
    }
    
    suspend fun update(list: List<ProgramDayExercise>) {
        programDayExerciseDao.update(list)
    }
}