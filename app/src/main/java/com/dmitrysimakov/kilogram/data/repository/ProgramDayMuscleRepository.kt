package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayMuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayMuscle

class ProgramDayMuscleRepository(private val dao: ProgramDayMuscleDao) {
    
    fun loadParams(programId: Long) = dao.getParamList(programId)
    
    suspend fun insert(list: List<ProgramDayMuscle>) {
        dao.insert(list)
    }
}