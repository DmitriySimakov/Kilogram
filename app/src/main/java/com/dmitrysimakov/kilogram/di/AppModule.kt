package com.dmitrysimakov.kilogram.di

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
    fun provideMuscleDao(db: KilogramDb) = db.muscleDao()

    @Singleton
    @Provides
    fun provideTrainingDao(db: KilogramDb) = db.trainingDao()

    @Singleton
    @Provides
    fun provideMeasurementDao(db: KilogramDb) = db.measurementDao()
}