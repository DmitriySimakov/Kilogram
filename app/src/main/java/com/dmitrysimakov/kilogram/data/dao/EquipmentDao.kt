package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.entity.Equipment

@Dao
interface EquipmentDao {
    
    @Query("SELECT * FROM equipment ORDER BY _id")
    fun getList(): LiveData<List<Equipment>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Equipment>)
}