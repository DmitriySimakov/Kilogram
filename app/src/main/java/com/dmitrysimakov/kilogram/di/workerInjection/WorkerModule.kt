package com.dmitrysimakov.kilogram.di.workerInjection

import androidx.work.Worker
import com.dmitrysimakov.kilogram.data.SeedDatabaseWorker
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [
    WorkerSubcomponent::class
])
abstract class WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(SeedDatabaseWorker::class)
    abstract fun bindMyWorkerFactory(builder: WorkerSubcomponent.Builder): AndroidInjector.Factory<out Worker>
}