package com.dmitrysimakov.kilogram.workers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.data.local.dao.TrainingExerciseDao
import com.dmitrysimakov.kilogram.data.local.dao.TrainingSetDao
import com.dmitrysimakov.kilogram.util.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.threeten.bp.OffsetDateTime
import com.dmitrysimakov.kilogram.data.local.entity.Training as LocalTraining
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise as LocalTrainingExercise
import com.dmitrysimakov.kilogram.data.local.entity.TrainingSet as LocalTrainingSet
import com.dmitrysimakov.kilogram.data.remote.models.Training as RemoteTraining
import com.dmitrysimakov.kilogram.data.remote.models.TrainingExercise as RemoteTrainingExercise
import com.dmitrysimakov.kilogram.data.remote.models.TrainingSet as RemoteTrainingSet

class SyncTrainingsWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val preferences: SharedPreferences by inject()
    
    private val trainingDao: TrainingDao by inject()
    private val trainingExerciseDao: TrainingExerciseDao by inject()
    private val trainingSetDao: TrainingSetDao by inject()
    
    override suspend fun doWork(): Result {
        try {
            val lastUpdate = preferences.getString(PreferencesKeys.TRAININGS_LAST_UPDATE, null)
            
            val trainingsTask = trainingsCollection.getNewDataTask(lastUpdate)
            val exercisesTask = trainingExercisesCollection.getNewDataTask(lastUpdate)
            val setsTask = trainingSetsCollection.getNewDataTask(lastUpdate)
            
            val remoteTrainings = trainingsTask.await().toObjects(RemoteTraining::class.java)
            val localTrainings = remoteTrainings.map {
                LocalTraining(it.id, it.startDateTime.toOffsetDateTime(), it.duration, it.programDayId)
            }
    
            val remoteTrainingExercises = exercisesTask.await().toObjects(RemoteTrainingExercise::class.java)
            val localTrainingExercises = remoteTrainingExercises.map {
                LocalTrainingExercise(it.id, it.trainingId, it.exercise, it.indexNumber, it.rest, it.strategy, it.state)
            }
    
            val remoteTrainingSets = setsTask.await().toObjects(RemoteTrainingSet::class.java)
            val localTrainingSets = remoteTrainingSets.map {
                LocalTrainingSet(it.id, it.trainingExerciseId, it.weight, it.reps, it.time, it.distance)
            }
    
            trainingDao.insert(localTrainings)
            trainingExerciseDao.insert(localTrainingExercises)
            trainingSetDao.insert(localTrainingSets)
            
            preferences.edit {
                putString(PreferencesKeys.TRAININGS_LAST_UPDATE, OffsetDateTime.now().toString())
            }
    
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
    
    private fun CollectionReference.getNewDataTask(lastUpdate: String?): Task<QuerySnapshot> {
        return if (lastUpdate == null) get()
        else whereGreaterThan("lastUpdate", lastUpdate).get()
    }
}