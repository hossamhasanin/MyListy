package com.hossam.hasanin.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.hossam.hasanin.base.ViewModelKey
import dagger.Provides


@Module
abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindProfileViewModel(viewModel: MainViewModel?): ViewModel?

}