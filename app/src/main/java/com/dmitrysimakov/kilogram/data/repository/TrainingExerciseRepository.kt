package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.DetailedTrainingExercise
import com.dmitrysimakov.kilogram.util.AppExecutors

class TrainingExerciseRepository(
        private val executors: AppExecutors,
        private val trainingExerciseDao: TrainingExerciseDao
) {
    
    fun loadDetailedTrainingExerciseList(trainingId: Long) = trainingExerciseDao.getDetailedTrainingExerciseList(trainingId)
    
    fun loadPrevTrainingExercise(trainingId: Long, exercise: String)
            = trainingExerciseDao.getPrevTrainingExercise(trainingId, exercise)
    
    fun loadTrainingExercise(id: Long) = trainingExerciseDao.getTrainingExercise(id)
    
    fun addExercise(exercise: TrainingExercise) {
        executors.diskIO().execute{
            trainingExerciseDao.insert(exercise)
        }
    }

    fun deleteExercise(exercise: DetailedTrainingExercise) {
        executors.diskIO().execute{
            trainingExerciseDao.deleteExerciseFromTraining(exercise._id)
        }
    }
    
    fun fillTrainingWithProgramExercises(trainingId: Long, programDayId: Long) {
        executors.diskIO().execute {
            trainingExerciseDao.fillTrainingWithProgramExercises(trainingId, programDayId)
        }
    }
    
    fun updateState(id: Long, state: Int) {
        executors.diskIO().execute {
            trainingExerciseDao.updateState(id, state)
        }
    }
    
    fun updateIndexNumbers(items: List<DetailedTrainingExercise>) {
        executors.diskIO().execute {
            trainingExerciseDao.updateIndexNumbers(items)
        }
    }
    
    fun finishTrainingExercises(trainingId: Long) {
        executors.diskIO().execute {
            trainingExerciseDao.finishTrainingExercises(trainingId, TrainingExercise.FINISHED, TrainingExercise.RUNNING)
        }
    }
}