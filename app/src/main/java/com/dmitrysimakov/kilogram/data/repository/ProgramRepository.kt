package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.util.AppExecutors

class ProgramRepository(
        private val executors: AppExecutors,
        private val programDao: ProgramDao
) {
    
    fun loadProgramList() = programDao.getProgramList()
    
    fun insertProgram(program: Program, callback: ((Long) -> Unit)? = null) {
        executors.diskIO().execute {
            val id = programDao.insert(program)
            executors.mainThread().execute {
                callback?.invoke(id)
            }
        }
    }
    
    fun deleteProgram(id: Long) {
        executors.diskIO().execute{
            programDao.delete(id)
        }
    }
}