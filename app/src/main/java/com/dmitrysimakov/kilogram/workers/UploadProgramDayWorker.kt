package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.remote.models.ProgramDay
import com.dmitrysimakov.kilogram.util.userProgramDaysCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: ProgramDayDao by inject()
    
    override suspend fun doWork(): Result {
        val programDayId = inputData.getLong(ID, 0)
        val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
        val programDay = if (needToDelete) {
            ProgramDay(programDayId, deleted = true)
        } else {
            val (_, programId, indexNumber, name, description) = dao.programDay(programDayId)
            ProgramDay(programDayId, programId, indexNumber, name, description)
        }
        
        userProgramDaysCollection.document(programDayId.toString()).set(programDay)
        
        return Result.success()
    }
}