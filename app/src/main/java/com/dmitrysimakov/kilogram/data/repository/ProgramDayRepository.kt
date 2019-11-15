package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProgramDayRepository(
        private val programDayDao: ProgramDayDao,
        private val io: CoroutineDispatcher = Dispatchers.IO
) {
    
    suspend fun loadProgramDays(programId: Long)  = withContext(io) {
        programDayDao.getProgramDayList(programId)
    }
    
    suspend fun loadNextProgramDayAndProgram() = withContext(io) {
        programDayDao.getNextProgramDay()
    }
    
    suspend fun loadProgramDayAndProgram(id: Long)  = withContext(io) {
        programDayDao.getProgramDayAndProgram(id)
    }
    
    suspend fun insertProgramDay(day: ProgramDay) = withContext(io) {
        programDayDao.insert(day)
    }
    
    suspend fun deleteProgramDay(id: Long) = withContext(io) {
        programDayDao.delete(id)
    }
    
    suspend fun update(items: List<ProgramDay>) = withContext(io) {
        programDayDao.update(items)
    }
}