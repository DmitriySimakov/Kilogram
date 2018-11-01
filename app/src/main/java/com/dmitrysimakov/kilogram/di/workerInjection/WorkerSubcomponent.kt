package com.dmitrysimakov.kilogram.di.workerInjection

import com.dmitrysimakov.kilogram.data.SeedDatabaseWorker
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface WorkerSubcomponent : AndroidInjector<SeedDatabaseWorker> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SeedDatabaseWorker>()
}