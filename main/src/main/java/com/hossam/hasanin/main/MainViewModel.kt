package com.hossam.hasanin.main

import androidx.lifecycle.ViewModel
import com.hossam.hasanin.base.repositories.MainRepository
import javax.inject.Inject

class MainViewModel  @Inject constructor(private val repository: MainRepository) : ViewModel() {
    fun show(){
        repository.show()
    }
}