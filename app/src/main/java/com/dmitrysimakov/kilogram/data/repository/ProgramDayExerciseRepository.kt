package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.relation.ProgramExerciseR
import com.dmitrysimakov.kilogram.util.AppExecutors

class ProgramDayExerciseRepository(
        private val executors: AppExecutors,
        private val programDayExerciseDao: ProgramDayExerciseDao
) {
   
    fun loadExercises(programDayId: Long) = programDayExerciseDao.getExercises(programDayId)
    
    fun addExerciseToProgramDay(exercise: ProgramDayExercise) {
        executors.diskIO().execute {
            programDayExerciseDao.insert(exercise)
        }
    }
    
    fun deleteExerciseFromProgramDay(exercise: ProgramExerciseR) {
        executors.diskIO().execute {
            programDayExerciseDao.delete(exercise._id)
        }
    }
    
    fun updateNums(items: List<ProgramExerciseR>) {
        executors.diskIO().execute {
            programDayExerciseDao.updateNums(items)
        }
    }
}