package com.dmitrysimakov.kilogram.workers

import android.content.Context
import android.content.SharedPreferences
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.KilogramDb
import com.dmitrysimakov.kilogram.data.model.*
import com.dmitrysimakov.kilogram.data.remote.*
import com.dmitrysimakov.kilogram.util.PreferencesKeys.DB_LAST_SYNC
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class SyncLocalDatabaseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val preferences: SharedPreferences by inject()
    private val db: KilogramDb by inject()
    
    private var lastUpdate = 0L
    
    override suspend fun doWork(): Result {
        try {
            lastUpdate = preferences.getLong(DB_LAST_SYNC, 0L)
            
            val programsTask = getNewDataTask(userProgramsCollection)
            val programDaysTask = getNewDataTask(userProgramDaysCollection)
            val programDayExercisesTask = getNewDataTask(userProgramDayExercisesCollection)
            val trainingsTask = getNewDataTask(userTrainingsCollection)
            val trainingExerciseTask = getNewDataTask(userTrainingExercisesCollection)
            val trainingSetsTask = getNewDataTask(userTrainingSetsCollection)
            val measurementsTask = getNewDataTask(userMeasurementsCollection)
            val photosTask = getNewDataTask(userPhotosCollection)
            
            syncPrograms(programsTask.await())
            syncProgramDays(programDaysTask.await())
            syncProgramDayExercises(programDayExercisesTask.await())
            syncTrainings(trainingsTask.await())
            syncTrainingExercises(trainingExerciseTask.await())
            syncTrainingSets(trainingSetsTask.await())
            syncMeasurements(measurementsTask.await())
            syncPhotos(photosTask.await())
            
            // TODO download photos from firebase storage
            
            preferences.edit().putLong(DB_LAST_SYNC, Date().time).apply()
    
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
    
    private suspend fun syncPrograms(snapshot: QuerySnapshot) {
        val dao = db.programDao()
        
        val items = snapshot.toObjects(Program::class.java)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        dao.insert(existingItems)
    }
    
    private suspend fun syncProgramDays(snapshot: QuerySnapshot) {
        val dao = db.programDayDao()
        
        val items = snapshot.toObjects(ProgramDay::class.java)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        dao.insert(existingItems)
    }
    
    private suspend fun syncProgramDayExercises(snapshot: QuerySnapshot) {
        val dao = db.programDayExerciseDao()
        
        val remoteItems = snapshot.toObjects(ProgramDayExercise::class.java)
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        dao.insert(existingItems)
    }
    
    private suspend fun syncTrainings(snapshot: QuerySnapshot) {
        val dao = db.trainingDao()
        
        val remoteItems = snapshot.toObjects(Training::class.java)
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        dao.insert(existingItems)
    }
    
    private suspend fun syncTrainingExercises(snapshot: QuerySnapshot) {
        val dao = db.trainingExerciseDao()
        val remoteItems = snapshot.toObjects(TrainingExercise::class.java)
    
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
        for (item in deletedItems) dao.delete(item.id)
        
        dao.insert(existingItems)
    }
    
    private suspend fun syncTrainingSets(snapshot: QuerySnapshot) {
        val dao = db.trainingSetDao()
        
        val remoteItems = snapshot.toObjects(TrainingSet::class.java)
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        dao.insert(existingItems)
    }
    
    private suspend fun syncMeasurements(snapshot: QuerySnapshot) {
        val dao = db.measurementDao()
        
        val items = snapshot.toObjects(Measurement::class.java)
        val (deletedItems, existingItems) = items.partition { it.deleted }
    
        for (item in deletedItems) dao.delete(item.id)
        dao.insert(existingItems)
    }
    
    private suspend fun syncPhotos(snapshot: QuerySnapshot) {
        val dao = db.photoDao()
        
        val items = snapshot.toObjects(Photo::class.java)
        val (deletedItems, existingItems) = items.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.uri)
        dao.insert(existingItems)
    }
    
    private fun getNewDataTask(ref: CollectionReference): Task<QuerySnapshot> {
        return if (lastUpdate == 0L) ref.get()
        else ref.whereGreaterThan("lastUpdate", Date(lastUpdate)).get()
    }
}