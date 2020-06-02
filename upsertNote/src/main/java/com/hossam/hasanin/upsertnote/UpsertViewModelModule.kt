package com.hossam.hasanin.upsertnote

import androidx.lifecycle.ViewModel
import com.hossam.hasanin.base.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UpsertViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UpsertNoteViewModel::class)
    abstract fun bindUpsertViewModel(viewModel: UpsertNoteViewModel?): ViewModel?

}