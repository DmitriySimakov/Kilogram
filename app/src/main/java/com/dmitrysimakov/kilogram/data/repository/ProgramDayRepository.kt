package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay

class ProgramDayRepository(private val dao: ProgramDayDao) {
    
    fun programDaysFlow(programId: Long)  = dao.programDaysFlow(programId)
    
    suspend fun nextProgramDayAndProgram() = dao.nextProgramDayAndProgram()
    
    suspend fun programDayAndProgram(id: Long)  = dao.programDayAndProgram(id)
    
    suspend fun insert(programDay: ProgramDay) = dao.insert(programDay)
    
    suspend fun delete(id: Long) = dao.delete(id)
    
    suspend fun update(programDays: List<ProgramDay>) = dao.update(programDays)
}