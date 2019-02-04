package com.dmitrysimakov.kilogram.di

import com.dmitrysimakov.kilogram.KilogramApp
import com.dmitrysimakov.kilogram.di.worker_injection.AndroidWorkerInjectionModule
import com.dmitrysimakov.kilogram.di.worker_injection.WorkerModule
import com.dmitrysimakov.kilogram.data.SeedDatabaseWorker
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AndroidWorkerInjectionModule::class,
            WorkerModule::class,
            AppModule::class,
            ActivityBuildersModule::class]
)
interface AppComponent : AndroidInjector<KilogramApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<KilogramApp>()

    fun inject(seedDatabaseWorker: SeedDatabaseWorker)
}