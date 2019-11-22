package com.dmitrysimakov.kilogram.data.local.relation

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class ExerciseMeasures (
        weight: Boolean = true,
        reps: Boolean = true,
        time: Boolean = false,
        distance: Boolean = false
) : BaseObservable() {
    
    @get:Bindable
    var weight = weight
        set(value) {
            field = value
            notifyPropertyChanged(BR.weight)
        }
    
    @get:Bindable
    var reps = reps
        set(value) {
            field = value
            notifyPropertyChanged(BR.reps)
        }
    
    @get:Bindable
    var time = time
        set(value) {
            field = value
            notifyPropertyChanged(BR.time)
        }
    
    @get:Bindable
    var distance = distance
        set(value) {
            field = value
            notifyPropertyChanged(BR.distance)
        }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ExerciseMeasures) return false
        return weight == other.weight && reps == other.reps && time == other.time && distance == other.distance
    }
    
    override fun hashCode(): Int {
        return weight.hashCode()*31*31*31 + reps.hashCode()*31*31 + time.hashCode()*31 + distance.hashCode()
    }
}