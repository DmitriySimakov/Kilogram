package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.workers.*

class ProgramSource(workManager: WorkManager) : RemoteDataSource(workManager) {
    
    fun uploadProgram(id: String) { upload(id, UploadProgramWorker::class.java) }
    fun deleteProgram(id: String) { delete(id, UploadProgramWorker::class.java) }
    
    fun uploadProgramDay(id: String) { upload(id, UploadProgramDayWorker::class.java) }
    fun deleteProgramDay(id: String) { delete(id, UploadProgramDayWorker::class.java) }
    
    fun uploadProgramDayList(programId: String) { upload(programId, UploadProgramDayListWorker::class.java) }
    
    fun uploadProgramDayExercise(id: String) { upload(id, UploadProgramDayExerciseWorker::class.java) }
    fun deleteProgramDayExercise(id: String) { delete(id, UploadProgramDayExerciseWorker::class.java) }
    
    fun uploadProgramDayExerciseList(programDayId: String) { upload(programDayId, UploadProgramDayExerciseListWorker::class.java) }
}