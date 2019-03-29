package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.entity.Muscle
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface MuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Muscle>)

    @Query("SELECT * FROM muscle ORDER BY _id")
    fun getMuscleList() : LiveData<List<Muscle>>
    
    @Query("SELECT _id, name FROM muscle ORDER BY _id")
    fun getParams() : LiveData<List<FilterParam>>
}