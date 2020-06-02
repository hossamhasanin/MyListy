package com.hossam.hasanin.main

import com.hossam.hasanin.base.dataSources.Dao
import com.hossam.hasanin.base.dataSources.MainDataSource
import com.hossam.hasanin.base.repositories.MainRepository
import dagger.Module
import dagger.Provides

@Module
class MainModuleDependencies {

    @Provides
    fun provideMainUseCase(repo: MainRepository): MainUseCase{
        return MainUseCase(repo)
    }

    @Provides
    fun provideNoteAdapter(): NotesAdapter{
        return NotesAdapter()
    }
}