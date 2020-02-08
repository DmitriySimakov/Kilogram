package com.dmitrysimakov.kilogram.data.remote.data_sources

import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.data.model.Program
import com.dmitrysimakov.kilogram.data.model.ProgramDay
import com.dmitrysimakov.kilogram.data.model.ProgramDayExercise
import com.dmitrysimakov.kilogram.data.remote.firestore
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
    
    fun publishProgram(program: Program) {
        firestore.document("programs/${program.id}").set(program)
    }
    
    fun publishProgramDay(programId: String, programDay: ProgramDay) {
        firestore.document("programs/$programId/program_days/${programDay.id}").set(programDay)
    }
    
    fun publishProgramDayExercise(programId: String, programDayId: String, programDayExercise: ProgramDayExercise) {
        firestore.document("programs/$programId/program_days/$programDayId/program_day_exercises/${programDayExercise.id}").set(programDayExercise)
    }
}