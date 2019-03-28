package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.entity.MechanicsType

@Dao
interface MechanicsTypeDao {

    @Query("SELECT * FROM mechanics_type ORDER BY _id")
    fun getList(): LiveData<List<MechanicsType>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<MechanicsType>)
}