package com.hossam.hasanin.mylisty.di

import com.hossam.hasanin.main.MainActivity
import com.hossam.hasanin.main.MainModuleDependencies
import com.hossam.hasanin.main.MainViewModelsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [
        MainModuleDependencies::class,
        MainViewModelsModule::class
    ])
    abstract fun bindMainActivity(): MainActivity

}