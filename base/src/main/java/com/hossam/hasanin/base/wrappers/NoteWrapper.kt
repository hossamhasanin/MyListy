package com.hossam.hasanin.base.wrappers

import androidx.recyclerview.widget.DiffUtil
import com.hossam.hasanin.base.models.Note

data class NoteWrapper (
    val note: Note?,
    val type: Int
){
    companion object {
        val CONTENT = 0
        val LOADING = 1

        val diffUtil = object : DiffUtil.ItemCallback<NoteWrapper>() {
            override fun areItemsTheSame(oldItem: NoteWrapper, newItem: NoteWrapper): Boolean {
                return oldItem.note?.title == newItem.note?.title
            }

            override fun areContentsTheSame(oldItem: NoteWrapper, newItem: NoteWrapper): Boolean {
                return Note.areContentsTheSame(oldItem.note ?: Note(-1 , "" , "") , newItem.note ?: Note(-1 , "" , ""))
            }
        }
    }
}