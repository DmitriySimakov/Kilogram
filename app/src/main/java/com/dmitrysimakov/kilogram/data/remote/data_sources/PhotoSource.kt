package com.dmitrysimakov.kilogram.data.remote.data_sources

import android.content.Context
import androidx.work.Data
import com.dmitrysimakov.kilogram.workers.UploadPhotoWorker

class PhotoSource(context: Context) : RemoteDataSource(context) {
    
    fun uploadPhoto(uri: String) {
        val data = Data.Builder().putString("uri", uri).build()
        upload(data, UploadPhotoWorker::class.java)
    }
}