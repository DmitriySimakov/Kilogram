package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.*
import com.dmitrysimakov.kilogram.data.remote.firebaseUser
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import java.util.*

const val ID = "id"
const val NEED_TO_DELETE = "need_to_delete"

abstract class RemoteDataSource(private val workManager: WorkManager) {
    
    fun scheduleUpload(id: String, workerClass: Class<out ListenableWorker>) {
        val data = Data.Builder().putString(ID, id).build()
        scheduleUpload(data, workerClass)
    }
    
    fun scheduleDeletion(id: String, workerClass: Class<out ListenableWorker>) {
        val data = Data.Builder()
                .putString(ID, id)
                .putBoolean(NEED_TO_DELETE, true)
                .build()
        scheduleUpload(data, workerClass)
    }
    
    fun scheduleUpload(data: Data, workerClass: Class<out ListenableWorker>) {
        if (firebaseUser == null) return
        
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build()
        
        val request = OneTimeWorkRequest.Builder(workerClass)
                .setConstraints(constraints)
                .setInputData(data)
                .build()
        
        workManager.enqueue(request)
    }
    
    protected suspend fun <T> getNewData(dataClass: Class<T>, ref: CollectionReference, lastUpdate: Long): List<T> {
        val query = if (lastUpdate == 0L) ref
        else ref.whereGreaterThan("lastUpdate", Date(lastUpdate))
        
        return query.get().await().toObjects(dataClass)
    }
}