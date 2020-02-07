package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.remote.data_sources.ID
import com.dmitrysimakov.kilogram.data.remote.firestore
import com.dmitrysimakov.kilogram.data.remote.userProgramDayExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayExerciseListWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: ProgramDayExerciseDao by inject()
    
    override suspend fun doWork(): Result {
        val programDayId = inputData.getString(ID)!!
        val exercises = dao.programDayExercises(programDayId)
        
        val writeBatch = firestore.batch()
        for (exercise in exercises) {
            writeBatch.set(userProgramDayExercisesCollection.document(exercise.id), exercise)
        }
        writeBatch.commit()
        
        return Result.success()
    }
}