package com.dmitrysimakov.kilogram.di

import android.content.Context
import android.content.SharedPreferences
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.dmitrysimakov.kilogram.KilogramApp
import com.dmitrysimakov.kilogram.data.KilogramDb
import com.dmitrysimakov.kilogram.data.SeedDatabaseWorker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    
    @Singleton
    @Provides
    fun provideSharedPreferences(app: KilogramApp): SharedPreferences {
        return app.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }
    
    @Singleton
    @Provides
    fun provideDb(app: KilogramApp): KilogramDb {
        return Room.databaseBuilder(app, KilogramDb::class.java, "kilogram.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                        WorkManager.getInstance().enqueue(request)
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideExerciseDao(db: KilogramDb) = db.exerciseDao()
    
    @Singleton
    @Provides
    fun provideProgramDao(db: KilogramDb) = db.programDao()
    
    @Singleton
    @Provides
    fun provideProgramDayDao(db: KilogramDb) = db.programDayDao()
    
    @Singleton
    @Provides
    fun provideProgramDayExerciseDao(db: KilogramDb) = db.programDayExerciseDao()

    @Singleton
    @Provides
    fun provideMuscleDao(db: KilogramDb) = db.muscleDao()

    @Singleton
    @Provides
    fun provideTrainingDao(db: KilogramDb) = db.trainingDao()

    @Singleton
    @Provides
    fun provideTrainingExerciseDao(db: KilogramDb) = db.trainingExerciseDao()

    @Singleton
    @Provides
    fun provideTrainingExerciseSetDao(db: KilogramDb) = db.trainingExerciseSetDao()

    @Singleton
    @Provides
    fun provideMeasurementDao(db: KilogramDb) = db.measurementDao()
}