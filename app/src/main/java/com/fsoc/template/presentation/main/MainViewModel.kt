package com.fsoc.template.presentation.main

import com.fsoc.template.presentation.base.BaseViewModel
import javax.inject.Inject

open class MainViewModel @Inject constructor() : BaseViewModel() {
    var listFavorite = ArrayList<String>()

    fun getData(){
        listFavorite.clear()
        for (i in 0..20) {
            listFavorite.add("Hello $i")
        }
    }
}