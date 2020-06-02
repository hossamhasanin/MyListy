package com.hossam.hasanin.base.repositories

import android.util.Log
import com.hossam.hasanin.base.dataSources.Dao
import com.hossam.hasanin.base.dataSources.MainDataSource
import com.hossam.hasanin.base.models.Note
import io.reactivex.Maybe
import javax.inject.Inject

class MainRepository (private val dataSource: Dao) {

    fun showNotes(lastNoteId: Int): Maybe<List<Note>>{
        return dataSource.getAllNotes(lastNoteId)
    }

}