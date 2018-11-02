package com.dmitrysimakov.kilogram.data

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dmitrysimakov.kilogram.data.entity.*
import com.dmitrysimakov.kilogram.di.workerInjection.AndroidWorkerInjection
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import javax.inject.Inject

class SeedDatabaseWorker(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val tag = this::class.java.simpleName

    @Inject lateinit var database: KilogramDb

    private var jsonReader: JsonReader? = null

    override fun doWork(): Result {
        AndroidWorkerInjection.inject(this)

        return try {
            with(database) {
                equipmentDao().insert(listFromJson("equipment.json", Array<Equipment>::class.java))
                exerciseTypeDao().insert(listFromJson("exercise_type.json", Array<ExerciseType>::class.java))
                mechanicsTypeDao().insert(listFromJson("mechanics_type.json", Array<MechanicsType>::class.java))
                muscleDao().insert(listFromJson("muscle.json", Array<Muscle>::class.java))
                exerciseDao().insert(listFromJson("exercise.json", Array<Exercise>::class.java))
                targetedMuscleDao().insert(listFromJson("targeted_muscle.json", Array<TargetedMuscle>::class.java))

                measurementParamDao().insert(listFromJson("measurement_param.json", Array<MeasurementParam>::class.java))
            }
            Result.SUCCESS
        } catch (e: Exception) {
            Log.e(tag, "Error seeding database", e)
            Result.FAILURE
        } finally {
            jsonReader?.close()
        }
    }

    private fun <T> listFromJson(filename: String, clazz: Class<Array<T>>): List<T> {
        val inputStream = context.assets.open(filename)
        jsonReader = JsonReader(inputStream.reader())
        val arr: Array<T> = Gson().fromJson(jsonReader, clazz)
        return arr.toList()
    }
}