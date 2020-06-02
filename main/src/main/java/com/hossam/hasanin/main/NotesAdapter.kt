package com.hossam.hasanin.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hossam.hasanin.base.models.Note
import com.hossam.hasanin.base.wrappers.NoteWrapper
import kotlinx.android.synthetic.main.note_item.view.*


class NotesAdapter:
    ListAdapter<NoteWrapper, NotesAdapter.ViewHolder>(NoteWrapper.diffUtil) {

    var goToAction: (Note?) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType){
            NoteWrapper.CONTENT -> {
                NoteViewHolder(layoutInflater.inflate(R.layout.note_item , parent , false))
            }
            NoteWrapper.LOADING -> {
                LoadingViewHolder(layoutInflater.inflate(R.layout.loading_item , parent , false))
            }
            else -> {
                throw IllegalStateException("Not allowed type")
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position , getItem(position) , goToAction)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        abstract fun onBind(pos: Int, note: NoteWrapper, goToAction: (Note?) -> Unit)
    }

    class LoadingViewHolder(view: View): ViewHolder(view){
        override fun onBind(pos: Int, note: NoteWrapper , goToAction: (Note?)-> Unit) {

        }
    }

    class NoteViewHolder(view: View) : ViewHolder(view){
        private val name = view.tv_name
        private val container = view.container

        override fun onBind(pos: Int, noteWrapper: NoteWrapper , goToAction: (Note?) -> Unit) {
            name.text = noteWrapper.note?.title
            container.setOnClickListener {
                goToAction(noteWrapper.note!!)
            }
        }

    }

}