package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProgramDayExerciseRepository(
        private val programDayExerciseDao: ProgramDayExerciseDao,
        private val io: CoroutineDispatcher = Dispatchers.IO
) {
   
    suspend fun loadProgramDayExerciseList(programDayId: Long) = withContext(io) {
        programDayExerciseDao.getProgramDayExerciseList(programDayId)
    }
    
    suspend fun addExerciseToProgramDay(exercise: ProgramDayExercise) = withContext(io) {
        programDayExerciseDao.insert(exercise)
    }
    
    suspend fun deleteExerciseFromProgramDay(exercise: ProgramDayExercise) = withContext(io) {
        programDayExerciseDao.delete(exercise)
    }
    
    suspend fun update(list: List<ProgramDayExercise>) = withContext(io) {
        programDayExerciseDao.update(list)
    }
}