package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.relation.DetailedTraining

@Dao
interface TrainingDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Training>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(training: Training) : Long
    
    @Query("""
        SELECT t._id, t.start_time, t.duration, t.program_day_id, pd.name AS program_day_name, p.name AS program_name
        FROM training AS t
        LEFT JOIN program_day AS pd ON pd._id = t.program_day_id
        LEFT JOIN program AS p ON p._id = pd.program_id
        ORDER BY t.start_time DESC""")
    suspend fun getDetailedTrainingList() : List<DetailedTraining>

    @Query("SELECT * FROM training WHERE _id = :id")
    suspend fun getTraining(id: Long) : Training
    
    @Update
    suspend fun update(training: Training)
    
    @Query("DELETE FROM training WHERE _id = :id")
    suspend fun delete(id: Long)
}