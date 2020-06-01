package com.hossam.hasanin.main

import com.hossam.hasanin.base.dataSources.MainDataSource
import com.hossam.hasanin.base.repositories.MainRepository
import dagger.Module
import dagger.Provides

@Module
class MainModuleDependencies {
    @Provides
    fun provideMainRepo(dataSource: MainDataSource): MainRepository{
        return MainRepository(dataSource)
    }

    @Provides
    fun provideMainDataSource(): MainDataSource{
        return MainDataSource()
    }
}