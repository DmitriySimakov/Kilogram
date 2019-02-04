package com.dmitrysimakov.kilogram.di.worker_injection

import androidx.work.Worker
import dagger.android.AndroidInjector

interface HasWorkerInjector {
    fun workerInjector(): AndroidInjector<Worker>
}