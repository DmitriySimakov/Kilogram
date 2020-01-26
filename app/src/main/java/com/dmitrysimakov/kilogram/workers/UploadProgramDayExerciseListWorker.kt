package com.dmitrysimakov.kilogram.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.data.remote.models.ProgramDayExercise
import com.dmitrysimakov.kilogram.util.firestore
import com.dmitrysimakov.kilogram.util.programDayExercisesCollection
import org.koin.core.KoinComponent
import org.koin.core.inject

class UploadProgramDayExerciseListWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val dao: ProgramDayExerciseDao by inject()
    
    override suspend fun doWork(): Result {
        val programDayId = inputData.getLong("id", 0)
        val exercises = dao.programDayExercises(programDayId)
        
        val writeBatch = firestore.batch()
        for (e in exercises) {
            val remoteExercise = ProgramDayExercise(e.id, e.programDayId, e.exercise, e.indexNumber, e.rest, e.strategy)
            writeBatch.set(programDayExercisesCollection.document(remoteExercise.id.toString()), remoteExercise)
        }
        writeBatch.commit()
        
        return Result.success()
    }
}