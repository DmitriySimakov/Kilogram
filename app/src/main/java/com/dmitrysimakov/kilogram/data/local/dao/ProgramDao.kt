package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dmitrysimakov.kilogram.data.local.entity.Program

@Dao
interface ProgramDao {
    
    @Query("SELECT * FROM program ORDER BY _id DESC")
    suspend fun programs() : List<Program>
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(programs: List<Program>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(program: Program) : Long
    
    
    @Query("DELETE FROM program WHERE _id = :id")
    suspend fun delete(id: Long)
}