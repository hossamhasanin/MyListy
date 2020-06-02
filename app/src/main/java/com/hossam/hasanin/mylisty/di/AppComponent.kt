package com.hossam.hasanin.mylisty.di

import android.app.Application
import com.hossam.hasanin.mylisty.BaseApplication
import com.hossam.hasanin.mylisty.di.viewModelProviders.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildersModule::class,
        AppModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Factory
    interface Factory {
        fun application(@BindsInstance application: Application?): AppComponent
    }
}