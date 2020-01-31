package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.workers.*

class ProgramSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    fun uploadProgram(id: Long) { upload(id, UploadProgramWorker::class.java) }
    fun deleteProgram(id: Long) { delete(id, UploadProgramWorker::class.java) }
    
    fun uploadProgramDay(id: Long) { upload(id, UploadProgramDayWorker::class.java) }
    fun deleteProgramDay(id: Long) { delete(id, UploadProgramDayWorker::class.java) }
    
    fun uploadProgramDayList(programId: Long) { upload(programId, UploadProgramDayListWorker::class.java) }
    
    fun uploadProgramDayExercise(id: Long) { upload(id, UploadProgramDayExerciseWorker::class.java) }
    fun deleteProgramDayExercise(id: Long) { delete(id, UploadProgramDayExerciseWorker::class.java) }
    
    fun uploadProgramDayExerciseList(programDayId: Long) { upload(programDayId, UploadProgramDayExerciseListWorker::class.java) }
}