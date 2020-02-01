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
            val trainingExerciseTask = getNewDataTask(trainingExercisesCollection)
            val trainingSetsTask = getNewDataTask(trainingSetsCollection)
            val measurementsTask = getNewDataTask(measurementsCollection)
            val photosTask = getNewDataTask(photosCollection)
            
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
        val remoteItems = snapshot.toObjects(RemoteProgram::class.java)
    
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        
        val localItems = existingItems.map { LocalProgram(it.id, it.name, it.description) }
        dao.insert(localItems)
    }
    
    private suspend fun syncProgramDays(snapshot: QuerySnapshot) {
        val dao = db.programDayDao()
        val remoteItems = snapshot.toObjects(RemoteProgramDay::class.java)
    
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        
        val localItems = existingItems.map { LocalProgramDay(it.id, it.programId, it.indexNumber, it.name, it.description) }
        dao.insert(localItems)
    }
    
    private suspend fun syncProgramDayExercises(snapshot: QuerySnapshot) {
        val dao = db.programDayExerciseDao()
        val remoteItems = snapshot.toObjects(RemoteProgramDayExercise::class.java)
    
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        
        val localItems = existingItems.map { LocalProgramDayExercise(it.id, it.programDayId, it.exercise, it.indexNumber, it.rest, it.strategy) }
        dao.insert(localItems)
    }
    
    private suspend fun syncTrainings(snapshot: QuerySnapshot) {
        val dao = db.trainingDao()
        val remoteItems = snapshot.toObjects(RemoteTraining::class.java)
    
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        
        val localItems = existingItems.map { LocalTraining(it.id, it.startDateTime.toOffsetDateTime(), it.duration, it.programDayId) }
        dao.insert(localItems)
    }
    
    private suspend fun syncTrainingExercises(snapshot: QuerySnapshot) {
        val dao = db.trainingExerciseDao()
        val remoteItems = snapshot.toObjects(RemoteTrainingExercise::class.java)
    
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        
        val localItems = existingItems.map { LocalTrainingExercise(it.id, it.trainingId, it.exercise, it.indexNumber, it.rest, it.strategy, it.state) }
        dao.insert(localItems)
    }
    
    private suspend fun syncTrainingSets(snapshot: QuerySnapshot) {
        val dao = db.trainingSetDao()
        val remoteItems = snapshot.toObjects(RemoteTrainingSet::class.java)
        
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.id)
        
        val localItems = existingItems.map {  LocalTrainingSet(it.id, it.trainingExerciseId, it.weight, it.reps, it.time, it.distance) }
        dao.insert(localItems)
    }
    
    private suspend fun syncMeasurements(snapshot: QuerySnapshot) {
        val dao = db.measurementDao()
        val remoteItems = snapshot.toObjects(RemoteMeasurement::class.java)
    
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
    
        for (item in deletedItems) dao.delete(item.id)
    
        val localItems = existingItems.map {  LocalMeasurement(it.id, it.date.toLocalDate(), it.param, it.value) }
        dao.insert(localItems)
    }
    
    private suspend fun syncPhotos(snapshot: QuerySnapshot) {
        val dao = db.photoDao()
        val remoteItems = snapshot.toObjects(RemotePhoto::class.java)
        
        val (deletedItems, existingItems) = remoteItems.partition { it.deleted }
        
        for (item in deletedItems) dao.delete(item.uri)
        
        val localItems = existingItems.map {  LocalPhoto(it.uri, it.dateTime.toOffsetDateTime()) }
        dao.insert(localItems)
    }
    
    private fun getNewDataTask(ref: CollectionReference): Task<QuerySnapshot> {
        return if (lastUpdate == 0L) ref.get()
        else ref.whereGreaterThan("lastUpdate", Date(lastUpdate)).get()
    }
}