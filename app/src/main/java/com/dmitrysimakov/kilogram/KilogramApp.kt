package com.dmitrysimakov.kilogram

import android.app.Activity
import android.app.Application
import android.app.Service
import androidx.work.Worker
import com.dmitrysimakov.kilogram.di.DaggerAppComponent
import com.dmitrysimakov.kilogram.di.worker_injection.HasWorkerInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import timber.log.Timber
import javax.inject.Inject

class KilogramApp : Application(), HasActivityInjector, HasServiceInjector, HasWorkerInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    
    @Inject lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    @Inject lateinit var workerInjector: DispatchingAndroidInjector<Worker>

    override fun onCreate() {
        DaggerAppComponent.builder().create(this).inject(this)
        super.onCreate()
    
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun activityInjector() = activityInjector
    
    override fun serviceInjector() = serviceInjector
    
    override fun workerInjector() = workerInjector
}