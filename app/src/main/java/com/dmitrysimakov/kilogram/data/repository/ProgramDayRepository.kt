package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay

class ProgramDayRepository(private val programDayDao: ProgramDayDao) {
    
    suspend fun loadProgramDays(programId: Long)  =
            programDayDao.getProgramDayList(programId)
    
    suspend fun loadNextProgramDayAndProgram() =
            programDayDao.getNextProgramDay()
    
    suspend fun loadProgramDayAndProgram(id: Long)  =
            programDayDao.getProgramDayAndProgram(id)
    
    suspend fun insertProgramDay(day: ProgramDay) {
        programDayDao.insert(day)
    }
    
    suspend fun deleteProgramDay(id: Long) {
        programDayDao.delete(id)
    }
    
    suspend fun update(items: List<ProgramDay>) {
        programDayDao.update(items)
    }
}