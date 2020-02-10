package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.model.ProgramDay
import com.dmitrysimakov.kilogram.data.remote.data_sources.ProgramSource

class ProgramDayRepository(
        private val dao: ProgramDayDao,
        private val src: ProgramSource
) {
    
    fun programDaysFlow(programId: String)  = dao.programDaysFlow(programId)
    
    suspend fun programDay(id: String) = dao.programDay(id)
    
    suspend fun nextProgramDayAndProgram() = dao.nextProgramDayAndProgram()
    
    suspend fun insert(programDay: ProgramDay) {
        dao.insert(programDay)
        src.uploadProgramDay(programDay.id)
    }
    
    suspend fun update(programDays: List<ProgramDay>) {
        if (programDays.isEmpty()) return
        
        dao.update(programDays)
        src.uploadProgramDayList(programDays[0].programId)
    }
    
    suspend fun delete(id: String) {
        dao.delete(id)
        src.deleteProgramDay(id)
    }
    
    suspend fun syncProgramDays(lastUpdate: Long) {
        val items = src.newProgramDays(lastUpdate)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        dao.insert(existingItems)
    }
}