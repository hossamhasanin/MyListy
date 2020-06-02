package com.hossam.hasanin.upsertnote

import androidx.lifecycle.ViewModel
import com.hossam.hasanin.base.models.Note
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

val TITLE = 0
val DESC = 1

class UpsertNoteViewModel @Inject constructor(private val useCase: UpsertUseCase) : ViewModel() {
    private val _viewState = BehaviorSubject.create<UpsertNoteViewState>().apply {
        onNext(UpsertNoteViewState(note = null , loading = false , editDescMode = false , editTitleMode = false , toolbarEditMode = false , error = null , done = false))
    }

    fun viewStateValue(): UpsertNoteViewState = _viewState.value!!

    fun viewState(): Observable<UpsertNoteViewState> = _viewState

    private val compositeDisposable = CompositeDisposable()
    private val _upsertingNote = PublishSubject.create<Unit>()

    init {
        bindUi()
    }

    fun bindUi(){
        val dis = _upsertNote()
            .doOnNext { postViewState(it) }
            .observeOn(AndroidSchedulers.mainThread()).subscribe({}){}
        compositeDisposable.add(dis)
    }

    private fun _upsertNote(): Observable<UpsertNoteViewState> {
        return _upsertingNote.switchMap { useCase.upsertNote(viewStateValue()) }
    }

    fun upserNote(note: Note){
        if (viewStateValue().loading) return
        postViewState(viewStateValue().copy(note= note , loading = true))
        _upsertingNote.onNext(Unit)
    }

    fun turnOnEditMode(element: Int){
        if (viewStateValue().editDescMode && viewStateValue().editTitleMode) return
        if (element == TITLE){
            postViewState(viewStateValue().copy(editTitleMode = true , toolbarEditMode = true))
        } else if (element == DESC){
            postViewState(viewStateValue().copy(editDescMode = true , toolbarEditMode = true))
        }
    }

    fun turnOffEditMode(){
        if (!viewStateValue().editDescMode && !viewStateValue().editTitleMode) return
        postViewState(viewStateValue().copy(editDescMode = false , editTitleMode = false , toolbarEditMode = false))
    }

    private fun postViewState(viewState: UpsertNoteViewState){
        _viewState.onNext(viewState)
    }

}