package com.hossam.hasanin.main

import androidx.lifecycle.ViewModel
import com.hossam.hasanin.base.wrappers.NoteWrapper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainViewModel  @Inject constructor(private val useCase: MainUseCase) : ViewModel() {
    private val _viewState = BehaviorSubject.create<MainViewState>().apply {
        onNext(MainViewState(notes = mutableListOf() , loading = true  , loadingMore = false , complete = false , empty = false , error = null))
    }

    fun viewStateValue(): MainViewState = _viewState.value!!

    fun viewState(): Observable<MainViewState> = _viewState

    private val compositeDisposable = CompositeDisposable()
    private val _loadingNotes = PublishSubject.create<Unit>()

    init {
        bindUi()
        _loadingNotes.onNext(Unit)
    }

    fun bindUi(){
        val dis = _loadNotes()
            .doOnNext { postViewState(it) }
            .observeOn(AndroidSchedulers.mainThread()).subscribe({}){}
        compositeDisposable.add(dis)
    }

    private fun _loadNotes(): Observable<MainViewState> {
        return _loadingNotes.switchMap { useCase.getNotes(viewStateValue()) }
    }

    fun getMore(){
        if (viewStateValue().loadingMore || viewStateValue().complete || viewStateValue().empty) return
        val l = viewStateValue().notes
        l.add(NoteWrapper(null , NoteWrapper.LOADING))
        postViewState(viewStateValue().copy(loadingMore = true , notes = l))
    }

    private fun postViewState(viewState: MainViewState){
        _viewState.onNext(viewState)
    }

}