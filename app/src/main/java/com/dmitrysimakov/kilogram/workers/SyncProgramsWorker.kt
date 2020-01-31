package com.dmitrysimakov.kilogram.workers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDao
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayDao
import com.dmitrysimakov.kilogram.data.local.dao.ProgramDayExerciseDao
import com.dmitrysimakov.kilogram.util.*
import kotlinx.coroutines.tasks.await
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.threeten.bp.OffsetDateTime
import com.dmitrysimakov.kilogram.data.local.entity.Program as LocalProgram
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDay as LocalProgramDay
import com.dmitrysimakov.kilogram.data.local.entity.ProgramDayExercise as LocalProgramDayExercise
import com.dmitrysimakov.kilogram.data.remote.models.Program as RemoteProgram
import com.dmitrysimakov.kilogram.data.remote.models.ProgramDay as RemoteProgramDay
import com.dmitrysimakov.kilogram.data.remote.models.ProgramDayExercise as RemoteProgramDayExercise

class SyncProgramsWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams), KoinComponent {
    
    private val preferences: SharedPreferences by inject()
    
    private val programDao: ProgramDao by inject()
    private val programDayDao: ProgramDayDao by inject()
    private val programDayExerciseDao: ProgramDayExerciseDao by inject()
    
    override suspend fun doWork(): Result {
        try {
            val lastUpdate = preferences.getString(PreferencesKeys.PROGRAMS_LAST_UPDATE, null)
            
            val programsTask = programsCollection.getNewDataTask(lastUpdate)
            val programDaysTask = programDaysCollection.getNewDataTask(lastUpdate)
            val programDayExercisesTask = programDayExercisesCollection.getNewDataTask(lastUpdate)
            
            val remotePrograms = programsTask.await().toObjects(RemoteProgram::class.java)
            val localPrograms = remotePrograms.map {
                LocalProgram(it.id, it.name, it.description)
            }
    
            val remoteProgramDays = programDaysTask.await().toObjects(RemoteProgramDay::class.java)
            val localProgramDays = remoteProgramDays.map {
                LocalProgramDay(it.id, it.programId, it.indexNumber, it.name, it.description)
            }
    
            val remoteProgramDayExercises = programDayExercisesTask.await().toObjects(RemoteProgramDayExercise::class.java)
            val localProgramDayExercises = remoteProgramDayExercises.map {
                LocalProgramDayExercise(it.id, it.programDayId, it.exercise, it.indexNumber, it.rest, it.strategy)
            }
    
            programDao.insert(localPrograms)
            programDayDao.insert(localProgramDays)
            programDayExerciseDao.insert(localProgramDayExercises)
            
            preferences.edit {
                putString(PreferencesKeys.PROGRAMS_LAST_UPDATE, OffsetDateTime.now().toString())
            }
    
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}