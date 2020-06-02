package com.hossam.hasanin.mylisty.di

import android.app.Application
import androidx.room.Room
import com.hossam.hasanin.base.dataSources.Dao
import com.hossam.hasanin.base.dataSources.Database
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

}