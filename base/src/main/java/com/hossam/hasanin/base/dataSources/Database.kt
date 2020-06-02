package com.hossam.hasanin.base.dataSources

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hossam.hasanin.base.models.Note

@Database(entities = [Note::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun notesDao() : Dao
}