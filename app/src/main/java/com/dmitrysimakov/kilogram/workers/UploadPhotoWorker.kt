package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.PhotoDao
import com.dmitrysimakov.kilogram.data.remote.data_sources.PHOTO_URI
import com.dmitrysimakov.kilogram.data.remote.models.Photo
import com.dmitrysimakov.kilogram.util.toIsoString
import com.dmitrysimakov.kilogram.util.userPhotosCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadPhotoWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: PhotoDao by inject()
    
    override suspend fun doWork(): Result {
        val uri = inputData.getString(PHOTO_URI)!!
        val (_, dateTime) = dao.photo(uri)
        
        val photo = Photo(uri, dateTime.toIsoString())
        userPhotosCollection.document(uri).set(photo)
        // TODO upload image to firebase storage
        
        return Result.success()
    }
}