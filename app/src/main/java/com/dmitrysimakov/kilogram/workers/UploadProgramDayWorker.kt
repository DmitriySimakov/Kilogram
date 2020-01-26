package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.models.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.programDaysCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repository: ProgramDayRepository by inject()
    
    override suspend fun doWork(): Result {
        val programDayId = inputData.getLong("id", 0)
        val (id, programId, indexNumber, name, description) = repository.programDay(programDayId)
        
        val programDay = ProgramDay(id, programId, indexNumber, name, description)
        programDaysCollection.document(id.toString()).set(programDay)
        
        return Result.success()
    }
}