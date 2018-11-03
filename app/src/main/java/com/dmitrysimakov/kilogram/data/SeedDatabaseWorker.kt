package com.dmitrysimakov.kilogram.data

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.di.workerInjection.AndroidWorkerInjection
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import javax.inject.Inject

class SeedDatabaseWorker(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val TAG = this::class.java.simpleName

    @Inject lateinit var database: KilogramDb

    override fun doWork(): Result {
        AndroidWorkerInjection.inject(this)

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
            Result.SUCCESS
        } catch (e: Exception) {
            Log.e(TAG, "Error seeding database", e)
            Result.FAILURE
        }
    }

    private inline fun <reified T : List<*>> listFromJson(filename: String): T {
        val inputStream = context.assets.open(filename)
        JsonReader(inputStream.reader()).use {
            return Gson().fromJson(it, object : TypeToken<T>() {}.type)
        }
    }
}