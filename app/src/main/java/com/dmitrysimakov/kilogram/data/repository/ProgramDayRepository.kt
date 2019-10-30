package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.util.AppExecutors

class ProgramDayRepository(
        private val executors: AppExecutors,
        private val programDayDao: ProgramDayDao
) {
    
    fun loadTrainingDays(programId: Long) = programDayDao.getProgramDayList(programId)
    
    fun insertProgramDay(day: ProgramDay, callback: ((Long) -> Unit)? = null) {
        executors.diskIO().execute {
            val id = programDayDao.insert(day)
            executors.mainThread().execute{
                callback?.invoke(id)
            }
        }
    }
    
    fun deleteProgramDay(id: Long) {
        executors.diskIO().execute {
            programDayDao.delete(id)
        }
    }
    
    fun update(items: List<ProgramDay>) {
        executors.diskIO().execute {
            programDayDao.update(items)
        }
    }
    
    fun loadNextProgramDayAndProgram() = programDayDao.getNextProgramDay()
    
    fun loadProgramDayAndProgram(id: Long) = programDayDao.getProgramDayAndProgram(id)
}