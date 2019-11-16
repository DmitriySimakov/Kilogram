package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay

class ProgramDayRepository(private val programDayDao: ProgramDayDao) {
    
    suspend fun programDays(programId: Long)  =
            programDayDao.programDays(programId)
    
    suspend fun nextProgramDayAndProgram() =
            programDayDao.nextProgramDayAndProgram()
    
    suspend fun programDayAndProgram(id: Long)  =
            programDayDao.programDayAndProgram(id)
    
    suspend fun insert(programDay: ProgramDay) =
            programDayDao.insert(programDay)
    
    suspend fun delete(id: Long) =
            programDayDao.delete(id)
    
    suspend fun update(programDays: List<ProgramDay>) =
            programDayDao.update(programDays)
}