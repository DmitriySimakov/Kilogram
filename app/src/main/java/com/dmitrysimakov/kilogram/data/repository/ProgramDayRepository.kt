package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramDayRepository @Inject constructor(
        private val executors: AppExecutors,
        private val programDayDao: ProgramDayDao
) {
    
    fun loadTrainingDays(programId: Long) = programDayDao.getTrainingDays(programId)
    
    fun insertProgramDay(day: ProgramDay, callback: ItemInsertedListener? = null) {
        executors.diskIO().execute {
            val id = programDayDao.insert(day)
            executors.mainThread().execute{
                callback?.onItemInserted(id)
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