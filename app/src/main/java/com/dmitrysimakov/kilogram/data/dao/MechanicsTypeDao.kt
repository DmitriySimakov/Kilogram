package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.entity.MechanicsType
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface MechanicsTypeDao {

    @Query("SELECT _id, name FROM mechanics_type ORDER BY _id")
    fun getParams(): LiveData<List<FilterParam>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<MechanicsType>)
}