package com.hossam.hasanin.upsertnote

import com.hossam.hasanin.base.repositories.MainRepository
import dagger.Module
import dagger.Provides


@Module
class UpsertNoteDependencyModule {

    @Provides
    fun provideMainUseCase(repo: MainRepository): UpsertUseCase{
        return UpsertUseCase(repo)
    }

}