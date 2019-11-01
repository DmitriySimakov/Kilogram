package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayMuscleDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayMuscle
import com.dmitrysimakov.kilogram.util.AppExecutors

class ProgramDayMuscleRepository(
        private val executors: AppExecutors,
        private val dao: ProgramDayMuscleDao
) {
    
    fun loadParams(programId: Long) = dao.getParamList(programId)
    
    fun insert(list: List<ProgramDayMuscle>) {
        executors.diskIO().execute {
            dao.insert(list)
        }
    }
}