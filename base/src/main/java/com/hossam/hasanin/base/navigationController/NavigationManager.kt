package com.hossam.hasanin.base.navigationController

import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class NavigationManager @Inject constructor() {
    private val _navigationStream = PublishSubject.create<NavigationModel>()

    fun navigationStream(): Observable<NavigationModel> = _navigationStream

    fun navigateTo(distination: Int , data: Bundle){
        _navigationStream.onNext(
            NavigationModel(
                distination,
                data
            )
        )
    }

    companion object{
        val MAIN = 0
        val UPSERT_NOTE = 1
    }

}