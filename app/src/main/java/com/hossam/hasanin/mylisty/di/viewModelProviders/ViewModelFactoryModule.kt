package com.hossam.hasanin.mylisty.di.viewModelProviders

import androidx.lifecycle.ViewModelProvider
import com.hossam.hasanin.mylisty.di.viewModelProviders.ViewModelFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory?): ViewModelProvider.Factory?


}