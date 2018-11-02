package com.dmitrysimakov.kilogram.di.workerInjection

import androidx.work.Worker
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.Multibinds

@Suppress("unused")
@Module
abstract class AndroidWorkerInjectionModule {

    @Multibinds
    abstract fun workerInjectorFactories(): Map<Class<out Worker>, AndroidInjector.Factory<out Worker>>
}