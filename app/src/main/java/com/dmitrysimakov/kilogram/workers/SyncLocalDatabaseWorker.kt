package com.dmitrysimakov.kilogram.workers

import android.content.Context
import android.content.SharedPreferences
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.KilogramDb
import com.dmitrysimakov.kilogram.util.*
import com.dmitrysimakov.kilogram.util.PreferencesKeys.DB_LAST_SYNC
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*
import com.dmitrysimakov.kilogram.data.local.entity.Measurement as LocalMeasurement
import com.dmitrysimakov.kilogram.data.local.entity.Photo as LocalPhoto
import com.dmitrysimakov.kilogram.data.local.entity.Program as LocalProgram
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay as LocalProgramDay
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise as LocalProgramDayExercise
import com.dmitrysimakov.kilogram.data.local.entity.Training as LocalTraining
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise as LocalTrainingExercise
import com.dmitrysimakov.kilogram.data.local.entity.TrainingSet as LocalTrainingSet
import com.dmitrysimakov.kilogram.data.remote.models.Measurement as RemoteMeasurement
import com.dmitrysimakov.kilogram.data.remote.models.Photo as RemotePhoto
import com.dmitrysimakov.kilogram.data.remote.models.Program as RemoteProgram
import com.dmitrysimakov.kilogram.data.remote.models.ProgramDay as RemoteProgramDay
import com.dmitrysimakov.kilogram.data.remote.models.ProgramDayExercise as RemoteProgramDayExercise
import com.dmitrysimakov.kilogram.data.remote.models.Training as RemoteTraining
import com.dmitrysimakov.kilogram.data.remote.models.TrainingExercise as RemoteTrainingExercise
import com.dmitrysimakov.kilogram.data.remote.models.TrainingSet as RemoteTrainingSet

class SyncLocalDatabaseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val preferences: SharedPreferences by inject()
    private val db: KilogramDb by inject()
    
    private var lastUpdate = 0L
    
    override suspend fun doWork(): Result {
        try {
            lastUpdate = preferences.getLong(DB_LAST_SYNC, 0L)
            
            val programsTask = getNewDataTask(programsCollection)
            val programDaysTask = getNewDataTask(programDaysCollection)
            val programDayExercisesTask = getNewDataTask(programDayExercisesCollection)
            val trainingsTask = getNewDataTask(trainingsCollection)
            val exercisesTask = getNewDataTask(trainingExercisesCollection)
            val setsTask = getNewDataTask(trainingSetsCollection)
            val measurementsTask = getNewDataTask(measurementsCollection)
            val photosTask = getNewDataTask(photosCollection)
            
            val remotePrograms = programsTask.await().toObjects(RemoteProgram::class.java)
            val localPrograms = remotePrograms.map {
                LocalProgram(it.id, it.name, it.description)
            }
    
            val remoteProgramDays = programDaysTask.await().toObjects(RemoteProgramDay::class.java)
            val localProgramDays = remoteProgramDays.map {
                LocalProgramDay(it.id, it.programId, it.indexNumber, it.name, it.description)
            }
    
            val remoteProgramDayExercises = programDayExercisesTask.await().toObjects(RemoteProgramDayExercise::class.java)
            val localProgramDayExercises = remoteProgramDayExercises.map {
                LocalProgramDayExercise(it.id, it.programDayId, it.exercise, it.indexNumber, it.rest, it.strategy)
            }
    
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
    
            val remoteMeasurements = measurementsTask.await().toObjects(RemoteMeasurement::class.java)
            val localMeasurements = remoteMeasurements.map {
                LocalMeasurement(it.id, it.date.toLocalDate(), it.param, it.value)
            }
    
            val remotePhotos = photosTask.await().toObjects(RemotePhoto::class.java)
            val localPhotos = remotePhotos.map {
                LocalPhoto(it.uri, it.dateTime.toOffsetDateTime())
            }
    
            db.programDao().insert(localPrograms)
            db.programDayDao().insert(localProgramDays)
            db.programDayExerciseDao().insert(localProgramDayExercises)
            db.trainingDao().insert(localTrainings)
            db.trainingExerciseDao().insert(localTrainingExercises)
            db.trainingSetDao().insert(localTrainingSets)
            db.measurementDao().insert(localMeasurements)
            db.photoDao().insert(localPhotos) // TODO download photos from firebase storage
            
            preferences.edit().putLong(DB_LAST_SYNC, Date().time).apply()
    
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
    
    private fun getNewDataTask(ref: CollectionReference): Task<QuerySnapshot> {
        return if (lastUpdate == 0L) ref.get()
        else ref.whereGreaterThan("lastUpdate", Date(lastUpdate)).get()
    }
}