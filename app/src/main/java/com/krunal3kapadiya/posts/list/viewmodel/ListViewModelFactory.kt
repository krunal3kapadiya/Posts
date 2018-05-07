package com.krunal3kapadiya.posts.list.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by Krunal on 12/22/2017.
 */
class ListViewModelFactory(private val dataSource: Any) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}