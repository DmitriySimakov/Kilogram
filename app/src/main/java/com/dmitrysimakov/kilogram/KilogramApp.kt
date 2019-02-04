package com.dmitrysimakov.kilogram

import android.app.Activity
import android.app.Application
import androidx.work.Worker
import com.dmitrysimakov.kilogram.di.DaggerAppComponent
import com.dmitrysimakov.kilogram.di.worker_injection.HasWorkerInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class KilogramApp : Application(), HasActivityInjector, HasWorkerInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject lateinit var workerInjector: DispatchingAndroidInjector<Worker>

    override fun onCreate() {
        DaggerAppComponent.builder().create(this).inject(this)
        super.onCreate()
    }

    override fun activityInjector() = activityInjector

    override fun workerInjector() = workerInjector
}