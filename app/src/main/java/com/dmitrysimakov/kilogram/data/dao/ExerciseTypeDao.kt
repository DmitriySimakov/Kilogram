package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.entity.ExerciseType

@Dao
interface ExerciseTypeDao {
    
    @Query("SELECT * FROM exercise_type ORDER BY _id")
    fun getList(): LiveData<List<ExerciseType>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<ExerciseType>)
}