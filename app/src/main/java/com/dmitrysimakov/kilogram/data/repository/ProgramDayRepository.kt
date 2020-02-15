package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.model.ProgramDay
import com.dmitrysimakov.kilogram.data.remote.data_sources.ProgramSource
import com.dmitrysimakov.kilogram.workers.UploadProgramDayListWorker
import com.dmitrysimakov.kilogram.workers.UploadProgramDayWorker

class ProgramDayRepository(
        private val dao: ProgramDayDao,
        private val src: ProgramSource
) {
    
    fun programDaysFlow(programId: String)  = dao.programDaysFlow(programId)
    
    suspend fun programDays(programId: String) = dao.programDays(programId)
    
    suspend fun publicProgramDay(programId: String, programDayId: String) =
            src.programDay(programId, programDayId)
    
    suspend fun publicProgramDays(programId: String) = src.programDays(programId)
    
    suspend fun programDay(id: String) = dao.programDay(id)
    
    suspend fun nextProgramDayAndProgram() = dao.nextProgramDayAndProgram()
    
    suspend fun insert(programDay: ProgramDay) {
        dao.insert(programDay)
        src.scheduleUpload(programDay.id, UploadProgramDayWorker::class.java)
    }
    
    suspend fun update(programDays: List<ProgramDay>) {
        if (programDays.isEmpty()) return
        
        dao.update(programDays)
        src.scheduleUpload(programDays[0].programId, UploadProgramDayListWorker::class.java)
    }
    
    suspend fun delete(id: String) {
        dao.delete(id)
        src.scheduleDeletion(id, UploadProgramDayWorker::class.java)
    }
    
    fun uploadProgramDay(programDay: ProgramDay) { src.uploadProgramDay(programDay) }
    
    fun uploadProgramDays(programDays: List<ProgramDay>) {
        programDays.forEach { src.uploadProgramDay(it) }
    }
    
    suspend fun syncProgramDays(lastUpdate: Long) {
        val items = src.newProgramDays(lastUpdate)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        deletedItems.forEach { dao.delete(it.id) }
        dao.insert(existingItems)
    }
}