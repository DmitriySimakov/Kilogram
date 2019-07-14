package com.dmitrysimakov.kilogram.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dmitrysimakov.kilogram.data.local.entity.Measurement
import com.dmitrysimakov.kilogram.data.relation.MeasurementR

@Dao
interface MeasurementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Measurement>)

    @Query("""
        SELECT _id, name AS param,
        (SELECT value FROM measurement
            WHERE param_id = bmp._id
            ORDER BY date DESC LIMIT 0, 1) AS value,
        (SELECT value FROM measurement
            WHERE param_id = bmp._id
            ORDER BY date DESC LIMIT 1, 1) AS prevValue,
        (SELECT date FROM measurement
            WHERE param_id = bmp._id
            ORDER BY date DESC LIMIT 0, 1) AS date,
        (SELECT date FROM measurement
            WHERE param_id = bmp._id
            ORDER BY date DESC LIMIT 1, 1) AS prevDate
        FROM measurement_param AS bmp ORDER BY _id
    """)
    fun getMeasurements() : LiveData<List<MeasurementR>>
}