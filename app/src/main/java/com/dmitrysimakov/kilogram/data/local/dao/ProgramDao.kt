package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.Program

@Dao
interface ProgramDao {
    
    @Query("SELECT * FROM program ORDER BY _id DESC")
    fun getProgramList() : LiveData<List<Program>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Program>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(program: Program) : Long
    
    @Query("DELETE FROM program WHERE _id = :id")
    fun delete(id: Long)
}