package com.dmitrysimakov.kilogram.di

import com.dmitrysimakov.kilogram.ui.EmailVerificationActivity
import com.dmitrysimakov.kilogram.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
    
    @ContributesAndroidInjector
    abstract fun contributeEmailVerificationActivity(): EmailVerificationActivity
}