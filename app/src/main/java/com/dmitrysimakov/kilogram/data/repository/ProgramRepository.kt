package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.data.remote.data_sources.ProgramSource

class ProgramRepository(
        private val programDao: ProgramDao,
        private val programDayDao: ProgramDayDao,
        private val programDayExerciseDao: ProgramDayExerciseDao,
        private val src: ProgramSource
) {
    
    fun programsFlow() = programDao.programsFlow()
    
    suspend fun insert(program: Program) {
        programDao.insert(program)
        src.uploadProgram(program.id)
    }
    
    suspend fun delete(id: String) {
        programDao.delete(id)
        src.deleteProgram(id)
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
    
    suspend fun syncPrograms(lastUpdate: Long) {
        val items = src.newPrograms(lastUpdate)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        for (item in deletedItems) programDao.delete(item.id)
        programDao.insert(existingItems)
    }
}