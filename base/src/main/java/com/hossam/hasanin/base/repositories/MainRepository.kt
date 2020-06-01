package com.hossam.hasanin.base.repositories

import android.util.Log
import com.hossam.hasanin.base.dataSources.MainDataSource
import javax.inject.Inject

class MainRepository (private val dataSource: MainDataSource) {

    fun show(){
        dataSource.show()
        Log.v("koko", "repo works")
    }

}