package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.local.entity.Program

class ProgramRepository(private val programDao: ProgramDao) {
    
    suspend fun programs() =
            programDao.programs()
    
    suspend fun insert(program: Program) =
            programDao.insert(program)
    
    suspend fun delete(id: Long) =
            programDao.delete(id)
}