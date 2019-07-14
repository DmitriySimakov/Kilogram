package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.Training
import com.dmitrysimakov.kilogram.data.relation.TrainingR

@Dao
interface TrainingDao {
    
    @Query("SELECT * FROM training ORDER BY start_time DESC")
    fun getTrainingList() : LiveData<List<Training>>
    
    @Query("""
        SELECT t._id, t.program_day_id, t.start_time, t.duration, pd.name AS program_day_name, p.name AS program_name
        FROM training AS t
        LEFT JOIN program_day AS pd
        ON pd._id = t.program_day_id
        LEFT JOIN program AS p
        ON p._id = pd.program_id
        ORDER BY t.start_time DESC""")
    fun getTrainingRList() : LiveData<List<TrainingR>>
    
    @Query("SELECT * FROM training WHERE _id = :id")
    fun getTraining(id: Long) : LiveData<Training>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(training: Training) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Training>)
    
    @Update
    fun update(training: Training)
    
    @Query("DELETE FROM training WHERE _id = :id")
    fun delete(id: Long)
}