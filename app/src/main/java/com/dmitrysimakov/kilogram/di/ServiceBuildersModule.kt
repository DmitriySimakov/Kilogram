package com.dmitrysimakov.kilogram.di

import com.dmitrysimakov.kilogram.service.MyFirebaseMessagingService
import com.dmitrysimakov.kilogram.ui.EmailVerificationActivity
import com.dmitrysimakov.kilogram.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ServiceBuildersModule {
    
    @ContributesAndroidInjector
    abstract fun contributeMyFirebaseMessagingService(): MyFirebaseMessagingService
}