package com.dmitrysimakov.kilogram.data.repository

import com.dmitrysimakov.kilogram.data.ItemInsertedListener
import com.dmitrysimakov.kilogram.data.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.dao.TrainingExerciseSetDao
import com.dmitrysimakov.kilogram.data.entity.Exercise
import com.dmitrysimakov.kilogram.data.entity.Training
import com.dmitrysimakov.kilogram.data.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.entity.TrainingExerciseSet
import com.dmitrysimakov.kilogram.data.relation.TrainingExerciseR
import com.dmitrysimakov.kilogram.util.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingRepository @Inject constructor(
        private val executors: AppExecutors,
        private val trainingDao: TrainingDao,
        private val trainingExerciseDao: TrainingExerciseDao,
        private val trainingExerciseSetDao: TrainingExerciseSetDao
) {

    fun loadTrainingList() = trainingDao.getTrainingList()

    fun insertTraining(training: Training, callback: ItemInsertedListener? = null) {
        executors.diskIO().execute{
            val id = trainingDao.insert(training)
            executors.mainThread().execute {
                callback?.onItemInserted(id)
            }
        }
    }

    fun deleteTraining(training: Training) {
        executors.diskIO().execute{
            trainingDao.delete(training)
        }
    }


    fun loadExercises(training_id: Long) = trainingExerciseDao.getExercises(training_id)

    fun addExercise(exercise_id: Long, training_id: Long) {
        executors.diskIO().execute{
            trainingExerciseDao.insert(TrainingExercise(training_id = training_id, exercise_id = exercise_id, number = 0))
        }
    }

    fun deleteExercise(exercise: TrainingExerciseR) {
        executors.diskIO().execute{
            trainingExerciseDao.deleteExerciseFromTraining(exercise._id)
        }
    }


    fun loadSets(training_exercise_id: Long) = trainingExerciseSetDao.getSets(training_exercise_id)

    fun loadSet(id: Long) = trainingExerciseSetDao.getSet(id)
    
    fun insertSet(set: TrainingExerciseSet) {
        executors.diskIO().execute{
            trainingExerciseSetDao.insert(set)
        }
    }

    fun deleteSet(set: TrainingExerciseSet) {
        executors.diskIO().execute{
            trainingExerciseSetDao.delete(set)
        }
    }
    
    fun updateSet(set: TrainingExerciseSet) {
        executors.diskIO().execute{
            trainingExerciseSetDao.update(set)
        }
    }
}