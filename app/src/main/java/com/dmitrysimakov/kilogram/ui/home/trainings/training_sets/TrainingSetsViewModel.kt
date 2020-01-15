package com.dmitrysimakov.kilogram.ui.home.trainings.training_sets

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.local.relation.SetWithPreviousResults
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingSetRepository
import com.dmitrysimakov.kilogram.util.Event
import com.dmitrysimakov.kilogram.util.live_data.AbsentLiveData
import com.dmitrysimakov.kilogram.util.setNewValue
import kotlinx.coroutines.launch
import kotlin.math.max

class TrainingSetsViewModel(
        private val trainingSetRepository: TrainingSetRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    
    private val _trainingExerciseId = MutableLiveData<Long>()
    
    val trainingExercise = _trainingExerciseId.switchMap {
        trainingExerciseRepository.trainingExerciseFlow(it).asLiveData()
    }
    
    val measures = trainingExercise.switchMap {
        liveData { emit(exerciseRepository.measures(it.exercise)) }
    }
    
    val currentSets = trainingExercise.switchMap {
        trainingSetRepository.trainingSetsFlow(it._id).asLiveData()
    }
    
    private val previousExercise = trainingExercise.switchMap {
        liveData { emit(trainingExerciseRepository.previousTrainingExercise(it.training_id, it.exercise)) }
    }
    
    val previousSets = previousExercise.switchMap {
        if (it == null) AbsentLiveData.create()
        else trainingSetRepository.trainingSetsFlow(it.training_exercise_id).asLiveData()
    }
    
    val sets = MediatorLiveData<List<SetWithPreviousResults>>()
    
    
    private val _trainingExerciseFinishedEvent = MutableLiveData<Event<Unit>>()
    val trainingExerciseFinishedEvent: LiveData<Event<Unit>> = _trainingExerciseFinishedEvent
    
    init {
        sets.addSource(currentSets) { sets.value = updateSets() }
        sets.addSource(previousSets) { sets.value = updateSets() }
    }
    
    fun setTrainingExerciseId(id: Long) = _trainingExerciseId.setNewValue(id)
    
    private fun updateSets(): List<SetWithPreviousResults> {
        val currSets = currentSets.value
        val prevSets = previousSets.value
        val res = mutableListOf<SetWithPreviousResults>()
        for (i in 0 until max(currSets?.size ?: 0, prevSets?.size ?: 0)) {
            val curr = try { currSets?.get(i) } catch (e: Exception) { null }
            val prev = try { prevSets?.get(i) } catch (e: Exception) { null }
            val set = SetWithPreviousResults(
                    curr?._id ?: 0, curr?.weight, curr?.reps, curr?.time, curr?.distance,
                    prev?._id ?: 0, prev?.weight, prev?.reps, prev?.time, prev?.distance
            )
            res.add(set)
        }
        return res
    }

    fun deleteSet(id: Long) = viewModelScope.launch {
        trainingSetRepository.delete(id)
    }
    
    fun finishExercise(trainingExerciseId: Long)= viewModelScope.launch {
        trainingExerciseRepository.updateState(trainingExerciseId, TrainingExercise.FINISHED)
        trainingExercise.value?.let { exerciseRepository.increaseExecutionsCnt(it.exercise) }
        _trainingExerciseFinishedEvent.value = Event(Unit)
    }
}