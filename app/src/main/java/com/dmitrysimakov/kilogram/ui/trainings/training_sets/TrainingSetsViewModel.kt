package com.dmitrysimakov.kilogram.ui.trainings.training_sets

import androidx.lifecycle.*
import com.dmitrysimakov.kilogram.data.local.entity.TrainingExercise
import com.dmitrysimakov.kilogram.data.local.entity.TrainingSet
import com.dmitrysimakov.kilogram.data.relation.SetWithPreviousResults
import com.dmitrysimakov.kilogram.data.repository.ExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseRepository
import com.dmitrysimakov.kilogram.data.repository.TrainingExerciseSetRepository
import com.dmitrysimakov.kilogram.util.Event
import kotlinx.coroutines.launch
import kotlin.math.max

class TrainingSetsViewModel(
        private val trainingExerciseSetRepository: TrainingExerciseSetRepository,
        private val trainingExerciseRepository: TrainingExerciseRepository,
        private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    
    private val _exercise = MutableLiveData<TrainingExercise>()
    val exercise: LiveData<TrainingExercise> =  _exercise
    
    private val _sets = MutableLiveData<List<SetWithPreviousResults>>()
    val sets: LiveData<List<SetWithPreviousResults>> = _sets
    
    private val _currentSets = MutableLiveData<List<TrainingSet>>()
    val currentSets: LiveData<List<TrainingSet>> =  _currentSets
    
    private val _previousSets = MutableLiveData<List<TrainingSet>>()
    val previousSets: LiveData<List<TrainingSet>> =  _previousSets
    
    private val _trainingExerciseFinishedEvent = MutableLiveData<Event<Unit>>()
    val trainingExerciseFinishedEvent: LiveData<Event<Unit>> = _trainingExerciseFinishedEvent
    
    fun start(exerciseId: Long, trainingId: Long) = viewModelScope.launch {
        val exercise = trainingExerciseRepository.trainingExercise(exerciseId)
        _exercise.value = exercise
    
        val previousExercise = trainingExerciseRepository.previousTrainingExercise(trainingId, exercise.exercise)
        
        val currSets = trainingExerciseSetRepository.trainingSets(exercise._id)
        
        val prevSets = if (previousExercise != null) {
            trainingExerciseSetRepository.trainingSets(previousExercise.training_exercise_id)
        } else {
            emptyList()
        }
    
        val res = mutableListOf<SetWithPreviousResults>()
        for (i in 0 until max(currSets.size, prevSets.size)) {
            val curr = try { currSets[i] } catch (e: Exception) { null }
            val prev = try { prevSets[i] } catch (e: Exception) { null }
            val set = SetWithPreviousResults(
                    curr?._id ?: 0,
                    curr?.weight,
                    curr?.reps,
                    curr?.time,
                    curr?.distance,
                    prev?._id ?: 0,
                    prev?.weight,
                    prev?.reps,
                    prev?.time,
                    prev?.distance
            )
            res.add(set)
        }
        _sets.value = res
    }

    fun deleteSet(id: Long) = viewModelScope.launch {
        trainingExerciseSetRepository.delete(id)
    }
    
    fun finishExercise(trainingExerciseId: Long)= viewModelScope.launch {
        trainingExerciseRepository.updateState(trainingExerciseId, TrainingExercise.FINISHED)
        exercise.value?.let { exerciseRepository.increaseExecutionsCnt(it.exercise) }
        _trainingExerciseFinishedEvent.value = Event(Unit)
    }
}