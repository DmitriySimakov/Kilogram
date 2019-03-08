package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingExerciseRepository @Inject constructor(
        private val executors: AppExecutors,
        private val trainingExerciseDao: TrainingExerciseDao
) {
    
    fun loadExercises(training_id: Long) = trainingExerciseDao.getExercises(training_id)
    
    fun loadExerciseMeasures(trainingExerciseId: Long) = trainingExerciseDao.getExerciseMeasures(trainingExerciseId)
    
    fun addExercise(exercise: TrainingExercise) {
        executors.diskIO().execute{
            trainingExerciseDao.insert(exercise)
        }
    }

    fun deleteExercise(exercise: TrainingExerciseR) {
        executors.diskIO().execute{
            trainingExerciseDao.deleteExerciseFromTraining(exercise._id)
        }
    }
    
    fun fillTrainingWithProgramExercises(trainingId: Long, programDayId: Long) {
        executors.diskIO().execute {
            trainingExerciseDao.fillTrainingWithProgramExercises(trainingId, programDayId)
        }
    }
}