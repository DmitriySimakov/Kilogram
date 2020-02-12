package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.model.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.remote.data_sources.ProgramSource
import com.dmitrysimakov.kilogram.workers.UploadProgramDayExerciseListWorker
import com.dmitrysimakov.kilogram.workers.UploadProgramDayExerciseWorker

class ProgramDayExerciseRepository(
        private val dao: ProgramDayExerciseDao,
        private val src: ProgramSource
) {
   
    fun programDayExercisesFlow(programDayId: String) = dao.programDayExercisesFlow(programDayId)
    
    suspend fun programDayExercises(programDayId: String) = dao.programDayExercises(programDayId)
    
    suspend fun programDayExercise(id: String) = dao.programDayExercise(id)
    
    suspend fun insert(programDayExercise: ProgramDayExercise) {
        dao.insert(programDayExercise)
        src.scheduleUpload(programDayExercise.id, UploadProgramDayExerciseWorker::class.java)
    }
    
    suspend fun update(programDayExercises: List<ProgramDayExercise>) {
        if (programDayExercises.isEmpty()) return
        
        dao.update(programDayExercises)
        src.scheduleUpload(programDayExercises[0].programDayId, UploadProgramDayExerciseListWorker::class.java)
    }
    
    suspend fun delete(id: String) {
        dao.delete(id)
        src.scheduleDeletion(id, UploadProgramDayExerciseWorker::class.java)
    }
    
    fun uploadProgramDayExercise(programDayExercise: ProgramDayExercise) {
        src.uploadProgramDayExercise(programDayExercise)
    }
    
    fun uploadProgramDayExercises(programDayExercises: List<ProgramDayExercise>) {
        programDayExercises.forEach { src.uploadProgramDayExercise(it) }
    }
    
    suspend fun syncProgramDayExercises(lastUpdate: Long) {
        val items = src.newProgramDayExercises(lastUpdate)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        deletedItems.forEach { dao.delete(it.id) }
        dao.insert(existingItems)
    }
}