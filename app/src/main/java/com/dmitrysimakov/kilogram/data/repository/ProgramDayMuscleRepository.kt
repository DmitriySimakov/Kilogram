package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayMuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayMuscle

class ProgramDayMuscleRepository(private val dao: ProgramDayMuscleDao) {
    
    suspend fun params(programId: Long) =
            dao.params(programId)
    
    suspend fun insert(programDayMuscles: List<ProgramDayMuscle>) =
            dao.insert(programDayMuscles)
}