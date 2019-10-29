package com.dmitrysimakov.kilogram.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.local.KilogramDb
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class SeedDatabaseWorker(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams), KoinComponent {
    
    private val database: KilogramDb by inject()

    override fun doWork(): Result {
        
        return try {
            with(database) {
                equipmentDao().insert(listFromJson("equipment.json"))
                exerciseTypeDao().insert(listFromJson("exercise_type.json"))
                mechanicsTypeDao().insert(listFromJson("mechanics_type.json"))
                muscleDao().insert(listFromJson("muscle.json"))
                exerciseDao().insert(listFromJson("exercise.json"))
                targetedMuscleDao().insert(listFromJson("targeted_muscle.json"))

                measurementParamDao().insert(listFromJson("measurement_param.json"))
            }
            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "Error seeding database")
            Result.failure()
        }
    }

    private inline fun <reified T : List<*>> listFromJson(filename: String): T {
        val inputStream = context.assets.open(filename)
        JsonReader(inputStream.reader()).use {
            return Gson().fromJson(it, object : TypeToken<T>() {}.type)
        }
    }
}