package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.repository.PhotoRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class UploadPhotoWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repo: PhotoRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            val uri = inputData.getString(ID)!!
            val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
            
            val photo = repo.photo(uri).copy(deleted = needToDelete, lastUpdate = Date())
            repo.uploadPhoto(photo)
            
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}