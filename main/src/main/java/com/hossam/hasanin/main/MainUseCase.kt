package com.hossam.hasanin.main

import com.hossam.hasanin.base.repositories.MainRepository
import com.hossam.hasanin.base.wrappers.NoteWrapper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MainUseCase(private val repo: MainRepository) {
    fun getNotes(viewState: MainViewState): Observable<MainViewState>{
        val l = viewState.notes
        val lastId = if (l.isEmpty()) 0 else l[l.lastIndex].note!!.id
        return repo.showNotes(lastId).materialize().map {
            it.value?.let {
                l.addAll(it.map { NoteWrapper(it , NoteWrapper.CONTENT) }.toMutableList())
                val isEmpty = l.isEmpty() && it.isEmpty()
                return@map viewState.copy(
                    notes = l,
                    error = null,
                    loading = false,
                    loadingMore = false,
                    complete = it.isEmpty(),
                    empty = isEmpty
                )
            }
            it.error?.let {
                return@map viewState.copy(
                    notes = mutableListOf(),
                    error = it as Exception,
                    loading = false,
                    loadingMore = false,
                    complete = false,
                    empty = false
                )
            }
            return@map viewState.copy(
                notes = mutableListOf(),
                error = null,
                loading = false,
                loadingMore = false,
                complete = false,
                empty = false
            )
        }.toObservable().subscribeOn(Schedulers.io())
    }
}