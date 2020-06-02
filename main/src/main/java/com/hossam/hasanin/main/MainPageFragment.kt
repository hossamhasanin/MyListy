package com.hossam.hasanin.main

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hossam.hasanin.base.externals.onEndReached
import com.hossam.hasanin.base.models.Note
import com.hossam.hasanin.base.wrappers.NoteWrapper
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_main_page.*
import javax.inject.Inject


class MainPageFragment : DaggerFragment() {

    private lateinit var model: MainViewModel
    @Inject lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var disposable: Disposable
    @Inject lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesAdapter.goToAction = {
            findNavController().navigate(Uri.parse("myListy://upsert/${it?.id}/${it?.title}/${it?.description}"))
        }

        model = ViewModelProvider(this , modelFactory)[MainViewModel::class.java]

//        button.setOnClickListener {
//            findNavController().navigate(Uri.parse("myListy://upsert/${Note(1 , "ti" , "des")}"))
//        }

        disposable = model.viewState().observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (it.loading){
                loading.visibility = View.VISIBLE
            } else {
                loading.visibility = View.GONE
            }

            if (it.error != null){
                tv_mess.visibility = View.VISIBLE
                tv_mess.text = it.error.localizedMessage
            } else {
                tv_mess.visibility = View.GONE
            }

            if (!it.loadingMore){
                if (notesAdapter.currentList.isNotEmpty()){
                    if (notesAdapter.currentList[notesAdapter.currentList.lastIndex].type == NoteWrapper.LOADING){
                        val l = notesAdapter.currentList
                        l.removeAt(l.lastIndex)
                        notesAdapter.submitList(l)
                    }
                }
            }

            if (it.empty){
                tv_mess.visibility = View.VISIBLE
                tv_mess.text = "There is no Notes , press on the + and add one"

            } else {
                tv_mess.visibility = View.GONE
            }

            if (it.notes.isNotEmpty()){
                notesAdapter.submitList(it.notes)
            }
        }

        rv_notes.onEndReached {
            model.getMore()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

}