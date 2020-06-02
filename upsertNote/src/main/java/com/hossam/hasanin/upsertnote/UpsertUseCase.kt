package com.hossam.hasanin.upsertnote

import com.hossam.hasanin.base.repositories.MainRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class UpsertUseCase(private val repository: MainRepository) {

    fun upsertNote(viewState: UpsertNoteViewState): Observable<UpsertNoteViewState>? {
        return repository.upsertNote(viewState.note!!).materialize<Unit>().map {
            return@map viewState.copy(loading = false , done = true)
        }.toObservable().subscribeOn(Schedulers.io())
    }

}