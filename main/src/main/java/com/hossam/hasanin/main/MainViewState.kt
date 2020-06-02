package com.hossam.hasanin.main

import com.hossam.hasanin.base.wrappers.NoteWrapper
import java.lang.Exception

data class MainViewState(
    val notes: MutableList<NoteWrapper>,
    val loading: Boolean,
    val loadingMore: Boolean,
    val complete: Boolean,
    val empty: Boolean,
    val error: Exception?
)