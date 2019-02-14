package com.dmitrysimakov.kilogram.data.relation

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.dmitrysimakov.kilogram.BR

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
    
}