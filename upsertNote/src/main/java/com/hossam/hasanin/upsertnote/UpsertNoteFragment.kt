package com.hossam.hasanin.upsertnote

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.hossam.hasanin.base.navigationController.NavigationManager
import com.hossam.hasanin.base.models.Note
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.upsert_note_fragment.*
import javax.inject.Inject

class UpsertNoteFragment : DaggerFragment() {

    private lateinit var model: UpsertNoteViewModel
    @Inject lateinit var modelFactory: ViewModelProvider.Factory
    @Inject lateinit var navigationManager: NavigationManager
    private lateinit var disposable: Disposable
    var isNew: Boolean = false
    var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.upsert_note_fragment, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(this , modelFactory)[UpsertNoteViewModel::class.java]


        arguments?.apply {
            isNew = getBoolean("new")
            note = getParcelable("note")
        }

        if (isNew){
            btn_add.visibility = View.VISIBLE
            titleEditMode()
            descEditMode()
        } else {
            btn_add.visibility = View.GONE
            et_title.setText(note?.title)
            et_desc.setText(note?.description)
        }


        disposable = model.viewState().observeOn(AndroidSchedulers.mainThread()).subscribe {
            Log.v("koko" , it.toString())
            if (it.loading){
                loading.visibility = View.VISIBLE
            } else {
                loading.visibility = View.GONE
            }

            if (it.error != null){
                et_title.setText("error")
                et_desc.setText(it.error.localizedMessage)
            }

            if (it.editTitleMode){
                titleEditMode()
            } else {
                titleViewMode()
            }

            if (it.editDescMode){
                descEditMode()
            } else {
                descViewMode()
            }

            if (it.done){
                navigationManager.navigateTo(NavigationManager.MAIN , Bundle().apply { putBoolean("done" , true) })
            }

            if (it.toolbarEditMode){
                toolbarEditMode()
            } else {
                toolbarViewMode()
            }
        }

        et_title.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && !isNew){
                model.turnOnEditMode(TITLE)
            }
        }

        et_desc.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && !isNew){
                model.turnOnEditMode(DESC)
            }
        }

        btn_add.setOnClickListener {
            addNote()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    fun titleEditMode(){
        et_title.background = ContextCompat.getDrawable(requireActivity() , android.R.drawable.edit_text)
    }

    fun titleViewMode(){
        et_title.clearFocus()
        et_title.background = null
    }

    fun descEditMode(){
        et_desc.background = ContextCompat.getDrawable(requireActivity() , android.R.drawable.edit_text)

    }

    fun descViewMode(){
        et_desc.clearFocus()
        et_desc.background = null
    }

    fun toolbarEditMode(){
        requireActivity().findViewById<AppCompatImageView>(R.id.right_btn).visibility = View.VISIBLE
        requireActivity().findViewById<AppCompatImageView>(R.id.left_btn).visibility = View.VISIBLE

        requireActivity().findViewById<AppCompatImageView>(R.id.right_btn).setOnClickListener {
            addNote()
            model.turnOffEditMode()
        }

        requireActivity().findViewById<AppCompatImageView>(R.id.left_btn).setOnClickListener {
            model.turnOffEditMode()
        }

    }

    fun toolbarViewMode(){
        requireActivity().findViewById<AppCompatImageView>(R.id.right_btn).visibility = View.GONE
        requireActivity().findViewById<AppCompatImageView>(R.id.left_btn).visibility = View.GONE

        requireActivity().findViewById<AppCompatImageView>(R.id.right_btn).setOnClickListener(null)
        requireActivity().findViewById<AppCompatImageView>(R.id.left_btn).setOnClickListener(null)
    }

    fun addNote(){
        if (et_title.text.toString().trim().isNotEmpty() && et_desc.text.toString().trim().isNotEmpty()){
            val note = Note(title = et_title.text.toString().trim() , description = et_desc.text.toString().trim())
            if (!isNew){
                note.id = this.note?.id
            }
            model.upserNote(note)
        }
    }


}