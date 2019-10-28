package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.Muscle
import com.dmitrysimakov.kilogram.data.relation.FilterParam

@Dao
interface MuscleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Muscle>)

    @Query("SELECT * FROM muscle ORDER BY _id")
    fun getMuscleList() : LiveData<List<Muscle>>
    
    @Query("SELECT _id, name, 0 AS is_active FROM muscle ORDER BY _id")
    fun getParams() : LiveData<List<FilterParam>>
    
    @Query("""SELECT m._id, m.name,
        CASE WHEN pdm.program_day_id IS NULL THEN 0 ELSE 1 END AS is_active
        FROM muscle AS m
        LEFT JOIN program_day_muscle AS pdm
        ON m._id = pdm.muscle_id AND pdm.program_day_id = :id
        ORDER BY m._id""")
    fun getProgramDayParams(id: Long) : LiveData<List<FilterParam>>
}