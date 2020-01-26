package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.remote.models.ProgramDay
import com.dmitrysimakov.kilogram.data.repository.ProgramDayRepository
import com.dmitrysimakov.kilogram.util.firestore
import com.dmitrysimakov.kilogram.util.programDaysCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayListWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val repository: ProgramDayRepository by inject()
    
    override suspend fun doWork(): Result {
        val programId = inputData.getLong("id", 0)
        val programDays = repository.programDays(programId)
        
        val writeBatch = firestore.batch()
        for (d in programDays) {
            val remoteDay = ProgramDay(d.id, d.programId, d.indexNumber, d.name, d.description)
            writeBatch.set(programDaysCollection.document(remoteDay.id.toString()), remoteDay)
        }
        writeBatch.commit()
        
        return Result.success()
    }
}