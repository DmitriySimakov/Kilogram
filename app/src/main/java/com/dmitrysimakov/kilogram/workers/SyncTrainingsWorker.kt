package com.dmitrysimakov.kilogram.workers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.TrainingDao
import com.dmitrysimakov.kilogram.util.PreferencesKeys
import com.dmitrysimakov.kilogram.util.toOffsetDateTime
import com.dmitrysimakov.kilogram.util.trainingsCollection
import kotlinx.coroutines.tasks.await
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.threeten.bp.OffsetDateTime
import com.dmitrysimakov.kilogram.data.local.entity.Training as LocalTraining
import com.dmitrysimakov.kilogram.data.remote.models.Training as RemoteTraining

class SyncTrainingsWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: TrainingDao by inject()
    private val preferences: SharedPreferences by inject()
    
    override suspend fun doWork(): Result {
        try {
            val lastUpdate = preferences.getString(PreferencesKeys.TRAINING_LAST_UPDATE, null)
    
            val getTrainingsQuery =  if (lastUpdate == null) trainingsCollection
            else trainingsCollection.whereGreaterThan("lastUpdate", lastUpdate)
    
            val remoteTrainings = getTrainingsQuery.get().await().toObjects(RemoteTraining::class.java)
            val localTrainings = remoteTrainings.map {
                LocalTraining(it.id, it.startDateTime.toOffsetDateTime(), it.duration, it.programDayId)
            }
    
            dao.insert(localTrainings)
            
            preferences.edit {
                putString(PreferencesKeys.TRAINING_LAST_UPDATE, OffsetDateTime.now().toString())
            }
    
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}