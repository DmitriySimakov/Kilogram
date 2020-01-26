package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.remote.data_sources.ProgramSource

class ProgramDayExerciseRepository(
        private val dao: ProgramDayExerciseDao,
        private val src: ProgramSource
) {
   
    fun programDayExercisesFlow(programDayId: Long) = dao.programDayExercisesFlow(programDayId)
    
    suspend fun programDayExercises(programDayId: Long) = dao.programDayExercises(programDayId)
    
    suspend fun programDayExercise(id: Long) = dao.programDayExercise(id)
    
    suspend fun insert(programDayExercise: ProgramDayExercise) {
        val id = dao.insert(programDayExercise)
        src.uploadProgramDayExercise(id)
    }
    
    suspend fun update(programDayExercises: List<ProgramDayExercise>) {
        if (programDayExercises.isEmpty()) return
        
        dao.update(programDayExercises)
        src.uploadProgramDayExerciseList(programDayExercises[0].programDayId)
    }
    
    suspend fun delete(id: Long) = dao.delete(id)
}