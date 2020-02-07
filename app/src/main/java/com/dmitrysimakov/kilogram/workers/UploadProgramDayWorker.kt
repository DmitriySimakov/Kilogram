package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.model.ProgramDay
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.data_sources.NEED_TO_DELETE
import com.dmitrysimakov.kilogram.data.remote.userProgramDaysCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: ProgramDayDao by inject()
    
    override suspend fun doWork(): Result {
        val programDayId = inputData.getString(ID)!!
        val needToDelete = inputData.getBoolean(NEED_TO_DELETE, false)
        
        val programDay = if (needToDelete) ProgramDay(id = programDayId, deleted = true)
        else dao.programDay(programDayId)
        
        userProgramDaysCollection.document(programDayId).set(programDay)
        
        return Result.success()
    }
}