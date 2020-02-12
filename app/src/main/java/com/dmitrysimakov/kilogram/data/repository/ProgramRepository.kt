package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.data.remote.data_sources.ProgramSource
import com.dmitrysimakov.kilogram.workers.UploadProgramWorker

class ProgramRepository(
        private val programDao: ProgramDao,
        private val programDayDao: ProgramDayDao,
        private val programDayExerciseDao: ProgramDayExerciseDao,
        private val src: ProgramSource
) {
    
    fun programsFlow() = programDao.programsFlow()
    
    suspend fun program(id: String) = programDao.program(id)
    
    suspend fun insert(program: Program) {
        programDao.insert(program)
        src.scheduleUpload(program.id, UploadProgramWorker::class.java)
    }
    
    suspend fun delete(id: String) {
        programDao.delete(id)
        src.scheduleDeletion(id, UploadProgramWorker::class.java)
    }
    
    suspend fun publishProgram(program: Program) {
        src.publishProgram(program)
        programDayDao.programDays(program.id).forEach { programDay ->
            src.publishProgramDay(program.id, programDay)
            programDayExerciseDao.programDayExercises(programDay.id).forEach { exercise ->
                src.publishProgramDayExercise(program.id, programDay.id, exercise)
            }
        }
    }
    
    fun uploadProgram(program: Program) { src.uploadProgram(program) }
    
    suspend fun syncPrograms(lastUpdate: Long) {
        val items = src.newPrograms(lastUpdate)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        deletedItems.forEach { programDao.delete(it.id) }
        programDao.insert(existingItems)
    }
}