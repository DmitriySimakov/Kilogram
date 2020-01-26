package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.data.remote.data_sources.ProgramSource

class ProgramDayRepository(
        private val dao: ProgramDayDao,
        private val src: ProgramSource
) {
    
    fun programDaysFlow(programId: Long)  = dao.programDaysFlow(programId)
    
    suspend fun programDays(programId: Long) = dao.programDays(programId)
    
    suspend fun programDay(id: Long) = dao.programDay(id)
    
    suspend fun nextProgramDayAndProgram() = dao.nextProgramDayAndProgram()
    
    suspend fun programDayAndProgram(id: Long)  = dao.programDayAndProgram(id)
    
    suspend fun insert(programDay: ProgramDay): Long {
        val id = dao.insert(programDay)
        src.uploadProgramDay(id)
        return id
    }
    
    suspend fun update(programDays: List<ProgramDay>) {
        if (programDays.isEmpty()) return
        
        dao.update(programDays)
        src.uploadProgramDayList(programDays[0].programId)
    }
    
    suspend fun delete(id: Long) = dao.delete(id)
}