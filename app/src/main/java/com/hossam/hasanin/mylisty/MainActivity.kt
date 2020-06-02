package com.hossam.hasanin.mylisty

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.hossam.hasanin.base.navigationController.NavigationManager
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var navigationManager: NavigationManager
    lateinit var disposable: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.topAppBar))
        setContentView(R.layout.activity_main)

        disposable = navigationManager.navigationStream().observeOn(AndroidSchedulers.mainThread()).subscribe {
            Log.v("koko" , it.toString())
            when(it.destination){
                NavigationManager.MAIN -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.mainPageFragment , it.data)
                }
                NavigationManager.UPSERT_NOTE -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.upsertNoteFragment , it.data)
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

}