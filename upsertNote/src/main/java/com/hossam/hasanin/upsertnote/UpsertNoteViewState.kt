package com.hossam.hasanin.upsertnote

import com.hossam.hasanin.base.models.Note

data class UpsertNoteViewState(
    val note: Note?,
    val error: Exception?,
    val loading: Boolean,
    val editTitleMode: Boolean,
    val editDescMode: Boolean,
    val done: Boolean,
    val toolbarEditMode: Boolean
)