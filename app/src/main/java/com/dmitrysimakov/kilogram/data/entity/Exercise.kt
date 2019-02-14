package com.dmitrysimakov.kilogram.data.entity

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.dmitrysimakov.kilogram.BR
import com.dmitrysimakov.kilogram.data.relation.ExerciseMeasures
import com.dmitrysimakov.kilogram.util.HasId

@Entity(tableName = "exercise",
        foreignKeys = [
            ForeignKey(
                    entity = Muscle::class,
                    parentColumns = ["_id"],
                    childColumns = ["main_muscle_id"],
                    onDelete = ForeignKey.SET_NULL),
            ForeignKey(
                    entity = MechanicsType::class,
                    parentColumns = ["_id"],
                    childColumns = ["mechanics_type_id"],
                    onDelete = ForeignKey.SET_NULL),
            ForeignKey(
                    entity = ExerciseType::class,
                    parentColumns = ["_id"],
                    childColumns = ["exercise_type_id"],
                    onDelete = ForeignKey.SET_NULL),
            ForeignKey(
                    entity = Equipment::class,
                    parentColumns = ["_id"],
                    childColumns = ["equipment_id"],
                    onDelete = ForeignKey.SET_NULL)
        ]
)
class Exercise(
        @PrimaryKey(autoGenerate = true) override val _id: Long = 0,
        val name: String,
        val main_muscle_id: Long? = null,
        val mechanics_type_id: Long? = null,
        val exercise_type_id: Long? = null,
        val equipment_id: Long? = null,
        val description: String? = null,
        weight: Boolean = true,
        reps: Boolean = true,
        time: Boolean = false,
        distance: Boolean = false
) : BaseObservable(), HasId {
    
    @get:Bindable var weight = weight
        set(value) {
            field = value
            notifyPropertyChanged(BR.weight)
        }
    
    @get:Bindable var reps = reps
        set(value) {
            field = value
            notifyPropertyChanged(BR.reps)
        }
    
    @get:Bindable var time = time
        set(value) {
            field = value
            notifyPropertyChanged(BR.time)
        }
    
    @get:Bindable var distance = distance
        set(value) {
            field = value
            notifyPropertyChanged(BR.distance)
        }
}