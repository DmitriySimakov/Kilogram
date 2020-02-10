package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.model.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.remote.data_sources.ProgramSource

class ProgramDayExerciseRepository(
        private val dao: ProgramDayExerciseDao,
        private val src: ProgramSource
) {
   
    fun programDayExercisesFlow(programDayId: String) = dao.programDayExercisesFlow(programDayId)
    
    suspend fun programDayExercises(programDayId: String) = dao.programDayExercises(programDayId)
    
    suspend fun insert(programDayExercise: ProgramDayExercise) {
        dao.insert(programDayExercise)
        src.uploadProgramDayExercise(programDayExercise.id)
    }
    
    suspend fun update(programDayExercises: List<ProgramDayExercise>) {
        if (programDayExercises.isEmpty()) return
        
        dao.update(programDayExercises)
        src.uploadProgramDayExerciseList(programDayExercises[0].programDayId)
    }
    
    suspend fun delete(id: String) {
        dao.delete(id)
        src.deleteProgramDayExercise(id)
    }
    
    suspend fun syncProgramDayExercises(lastUpdate: Long) {
        val items = src.newProgramDayExercises(lastUpdate)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        dao.insert(existingItems)
    }
}