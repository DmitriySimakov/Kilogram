package com.dmitrysimakov.kilogram.workers

import android.content.Context
import android.content.SharedPreferences
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.repository.*
import com.dmitrysimakov.kilogram.util.PreferencesKeys.DB_LAST_SYNC
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class SyncLocalDatabaseWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val preferences: SharedPreferences by inject()
    
    private val measurementRepo: MeasurementRepository by inject()
    private val photoRepo: PhotoRepository by inject()
    private val programRepo: ProgramRepository by inject()
    private val programDayRepo: ProgramDayRepository by inject()
    private val programDayExerciseRepo: ProgramDayExerciseRepository by inject()
    private val trainingRepo: TrainingRepository by inject()
    private val trainingExerciseRepo: TrainingExerciseRepository by inject()
    private val trainingSetRepo: TrainingSetRepository by inject()
    
    private var lastUpdate = 0L
    
    override suspend fun doWork(): Result {
        return try {
            lastUpdate = preferences.getLong(DB_LAST_SYNC, 0L)
            
            measurementRepo.syncMeasurements(lastUpdate)
            photoRepo.syncPhotos(lastUpdate)
            programRepo.syncPrograms(lastUpdate)
            programDayRepo.syncProgramDays(lastUpdate)
            programDayExerciseRepo.syncProgramDayExercises(lastUpdate)
            trainingRepo.syncTrainings(lastUpdate)
            trainingExerciseRepo.syncTrainingExercises(lastUpdate)
            trainingSetRepo.syncTrainingSets(lastUpdate)
            
            preferences.edit().putLong(DB_LAST_SYNC, Date().time).apply()
    
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}