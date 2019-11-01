package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay

class ProgramDayRepository(private val programDayDao: ProgramDayDao) {
    
    fun loadTrainingDays(programId: Long) = programDayDao.getProgramDayList(programId)
    
    suspend fun insertProgramDay(day: ProgramDay, callback: ((Long) -> Unit)? = null) {
        val id = programDayDao.insert(day)
        callback?.invoke(id)
    }
    
    suspend fun deleteProgramDay(id: Long) {
        programDayDao.delete(id)
    }
    
    suspend fun update(items: List<ProgramDay>) {
        programDayDao.update(items)
    }
    
    fun loadNextProgramDayAndProgram() = programDayDao.getNextProgramDay()
    
    fun loadProgramDayAndProgram(id: Long) = programDayDao.getProgramDayAndProgram(id)
}