package com.hossam.hasanin.base.dataSources

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hossam.hasanin.base.externals.MAX_DATA_RESULTS
import com.hossam.hasanin.base.models.Note
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface Dao {

    @Query("SELECT * FROM notes WHERE id > :id LIMIT $MAX_DATA_RESULTS")
    fun getAllNotes(id: Int): Maybe<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE , entity = Note::class)
    fun upSertNote(note: Note): Completable

}