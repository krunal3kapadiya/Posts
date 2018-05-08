package com.krunal3kapadiya.posts.detail.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.krunal3kapadiya.posts.common.dao.CommentsDao

/**
 * Created by Krunal on 08/05/15.
 */
class DetailModelFactory(private val commentsDao: CommentsDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(commentsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}