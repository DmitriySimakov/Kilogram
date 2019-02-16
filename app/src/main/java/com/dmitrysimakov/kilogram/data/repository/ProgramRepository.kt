package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.entity.Program
import com.dmitrysimakov.kilogram.data.entity.ProgramDay
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramRepository @Inject constructor(
        private val executors: AppExecutors,
        private val programDao: ProgramDao,
        private val programDayDao: ProgramDayDao
) {
    
    fun loadProgramList() = programDao.getProgramList()
    
    fun insertProgram(program: Program, callback: ItemInsertedListener? = null) {
        executors.diskIO().execute {
            val id = programDao.insert(program)
            executors.mainThread().execute {
                callback?.onItemInserted(id)
            }
        }
    }
    
    fun deleteProgram(program: Program) {
        executors.diskIO().execute{
            programDao.delete(program)
        }
    }
    
    
    fun loadTrainingDays(programId: Long) = programDayDao.getTrainingDays(programId)
    
    fun insertProgramDay(day: ProgramDay, callback: ItemInsertedListener? = null) {
        executors.diskIO().execute {
            val id = programDayDao.insert(day)
            executors.mainThread().execute{
                callback?.onItemInserted(id)
            }
        }
    }
    
    fun deleteTrainingDay(day: ProgramDay) {
        executors.diskIO().execute {
            programDayDao.delete(day)
        }
    }
}