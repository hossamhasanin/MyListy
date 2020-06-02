package com.hossam.hasanin.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hossam.hasanin.base.navigationController.NavigationManager
import com.hossam.hasanin.base.externals.onEndReached
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
    @Inject lateinit var navigationManager: NavigationManager
    var done: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarViewMode()

        arguments?.apply {
            done = getBoolean("done")

            if (done){
                Snackbar.make(cont , "Saved successfully !" , Snackbar.LENGTH_LONG).show()
            }
        }

        notesAdapter.goToAction = {
            navigationManager.navigateTo(NavigationManager.UPSERT_NOTE , Bundle().apply { putBoolean("new" , false)
                putParcelable("note" , it) })
        }

        model = ViewModelProvider(this , modelFactory)[MainViewModel::class.java]

//        button.setOnClickListener {
//            findNavController().navigate(Uri.parse("myListy://upsert/${Note(1 , "ti" , "des")}"))
//        }

        disposable = model.viewState().observeOn(AndroidSchedulers.mainThread()).subscribe {
            Log.v("koko" , it.toString())
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

        rv_notes.layoutManager = LinearLayoutManager(activity)
        rv_notes.adapter = notesAdapter

        rv_notes.onEndReached {
            model.getMore()
        }

        btn_add.setOnClickListener {
            navigationManager.navigateTo(NavigationManager.UPSERT_NOTE , Bundle().apply { putBoolean("new" , true) })
        }

    }

    fun toolbarViewMode(){
        requireActivity().findViewById<AppCompatImageView>(R.id.right_btn).visibility = View.GONE
        requireActivity().findViewById<AppCompatImageView>(R.id.left_btn).visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

}