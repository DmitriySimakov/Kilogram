package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repo: ProgramDayRepository by inject()
    
    override suspend fun doWork(): Result {
        return try {
            val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
            var programDay = repo.programDay(inputData.getString(ID)!!)
            if (needToDelete) programDay = programDay.copy(deleted = true)
        
            repo.uploadProgramDay(programDay)
        
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}