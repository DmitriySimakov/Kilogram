package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.PhotoDao
import com.dmitrysimakov.kilogram.data.remote.models.Photo
import com.dmitrysimakov.kilogram.util.photosCollection
import com.dmitrysimakov.kilogram.util.toIsoString
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadPhotoWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: PhotoDao by inject()
    
    override suspend fun doWork(): Result {
        val uri = inputData.getString("uri")!!
        val (_, dateTime) = dao.photo(uri)
        
        val photo = Photo(uri, dateTime.toIsoString())
        photosCollection.document(uri).set(photo)
        // TODO upload image to firebase storage
        
        return Result.success()
    }
}