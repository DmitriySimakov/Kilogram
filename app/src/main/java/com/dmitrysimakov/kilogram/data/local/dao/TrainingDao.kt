package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTraining
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

@Dao
interface TrainingDao {
    
    @Query("""
        SELECT T.id, T.startDateTime, T.duration, T.programDayId, PD.name AS programDayName, P.name AS programName
        FROM Training AS T
        LEFT JOIN ProgramDay AS PD ON PD.id = T.programDayId
        LEFT JOIN Program AS P ON P.id = PD.programId
        ORDER BY T.startDateTime DESC""")
    fun detailedTrainingsFlow() : Flow<List<DetailedTraining>>
    
    @Query("""
        SELECT T.id, T.startDateTime, T.duration, T.programDayId, PD.name AS programDayName, P.name AS programName
        FROM Training AS T
        LEFT JOIN ProgramDay AS PD ON PD.id = T.programDayId
        LEFT JOIN Program AS P ON P.id = PD.programId
        WHERE date(startDateTime) = date(:startDateTime)
        ORDER BY T.startDateTime""")
    suspend fun detailedTrainingsForDay(startDateTime: LocalDate) : List<DetailedTraining>
    
    @Query("SELECT * FROM Training WHERE id = :id")
    suspend fun training(id: Long) : Training
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainings: List<Training>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(training: Training) : Long
    
    
    @Update
    suspend fun update(training: Training)
    
    
    @Query("DELETE FROM Training WHERE id = :id")
    suspend fun delete(id: Long)
}