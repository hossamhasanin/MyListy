package com.hossam.hasanin.mylisty.di

import android.app.Application
import androidx.room.Room
import com.hossam.hasanin.base.navigationController.NavigationManager
import com.hossam.hasanin.base.dataSources.Dao
import com.hossam.hasanin.base.dataSources.Database
import com.hossam.hasanin.base.repositories.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application?): Database{
        return Room.databaseBuilder(application!! , Database::class.java, "mylisty_database").build()
    }

    @Singleton
    @Provides
    fun notesDao(database: Database): Dao{
        return database.notesDao()
    }

    @Singleton
    @Provides
    fun provideMainRepo(dataSource: Dao): MainRepository {
        return MainRepository(dataSource)
    }

    @Singleton
    @Provides
    fun providen(dataSource: Dao): NavigationManager {
        return NavigationManager()
    }

}