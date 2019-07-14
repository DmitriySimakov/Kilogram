package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.local.entity.Program
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramRepository @Inject constructor(
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
    
    fun deleteProgram(program: Program) {
        executors.diskIO().execute{
            programDao.delete(program)
        }
    }
}