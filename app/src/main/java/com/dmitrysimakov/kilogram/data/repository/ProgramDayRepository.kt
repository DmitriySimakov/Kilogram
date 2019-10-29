package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay
import com.dmitrysimakov.kilogram.util.AppExecutors

class ProgramDayRepository(
        private val executors: AppExecutors,
        private val programDayDao: ProgramDayDao
) {
    
    fun loadTrainingDays(programId: Long) = programDayDao.getTrainingDays(programId)
    
    fun insertProgramDay(day: ProgramDay, callback: ((Long) -> Unit)? = null) {
        executors.diskIO().execute {
            val id = programDayDao.insert(day)
            executors.mainThread().execute{
                callback?.invoke(id)
            }
        }
    }
    
    fun update(programDay: ProgramDay) {
        executors.diskIO().execute {
            programDayDao.update(programDay)
        }
    }
    
    fun deleteProgramDay(day: ProgramDay) {
        executors.diskIO().execute {
            programDayDao.delete(day)
        }
    }
    
    fun updateNums(items: List<ProgramDay>) {
        executors.diskIO().execute {
            programDayDao.updateNums(items)
        }
    }
    
    fun loadNextProgramDayR() = programDayDao.getNextProgramDayR()
    
    fun loadProgramDayR(id: Long) = programDayDao.getProgramDayR(id)
}