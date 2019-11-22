package com.dmitrysimakov.kilogram.data.local.dao

import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.local.relation.DetailedTraining
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {
    
    @Query("""
        SELECT t._id, t.start_time, t.duration, t.program_day_id, pd.name AS program_day_name, p.name AS program_name
        FROM training AS t
        LEFT JOIN program_day AS pd ON pd._id = t.program_day_id
        LEFT JOIN program AS p ON p._id = pd.program_id
        ORDER BY t.start_time DESC""")
    fun detailedTrainingsFlow() : Flow<List<DetailedTraining>>

    @Query("SELECT * FROM training WHERE _id = :id")
    suspend fun training(id: Long) : Training
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainings: List<Training>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(training: Training) : Long
    
    
    @Update
    suspend fun update(training: Training)
    
    
    @Query("DELETE FROM training WHERE _id = :id")
    suspend fun delete(id: Long)
}