package com.hossam.hasanin.mylisty

import android.app.Application
import com.hossam.hasanin.mylisty.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().application(applicationContext as Application?)
    }
}