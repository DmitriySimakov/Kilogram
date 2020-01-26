package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.remote.models.ProgramDay
import com.dmitrysimakov.kilogram.util.programDaysCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: ProgramDayDao by inject()
    
    override suspend fun doWork(): Result {
        val programDayId = inputData.getLong("id", 0)
        val (_, programId, indexNumber, name, description) = dao.programDay(programDayId)
        
        val programDay = ProgramDay(programDayId, programId, indexNumber, name, description)
        programDaysCollection.document(programDayId.toString()).set(programDay)
        
        return Result.success()
    }
}