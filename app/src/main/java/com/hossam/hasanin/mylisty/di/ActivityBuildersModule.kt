package com.hossam.hasanin.mylisty.di

import com.hossam.hasanin.main.MainModuleDependencies
import com.hossam.hasanin.main.MainPageFragment
import com.hossam.hasanin.main.MainViewModelsModule
import com.hossam.hasanin.mylisty.MainActivity
import com.hossam.hasanin.upsertnote.UpsertNoteDependencyModule
import com.hossam.hasanin.upsertnote.UpsertNoteFragment
import com.hossam.hasanin.upsertnote.UpsertViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [
        MainModuleDependencies::class,
        MainViewModelsModule::class
    ])
    abstract fun bindMainFragment(): MainPageFragment

    @ContributesAndroidInjector(modules = [
        UpsertNoteDependencyModule::class,
        UpsertViewModelModule::class
    ])
    abstract fun bindUpsertFragment(): UpsertNoteFragment

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}