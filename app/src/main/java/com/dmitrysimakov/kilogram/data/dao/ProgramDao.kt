package com.dmitrysimakov.kilogram.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.entity.Program

@Dao
interface ProgramDao {
    
    @Query("SELECT * FROM program ORDER BY _id DESC")
    fun getProgramList() : LiveData<List<Program>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Program>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(program: Program) : Long
    
    @Delete
    fun delete(program: Program)
}