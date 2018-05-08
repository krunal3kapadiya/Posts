package com.krunal3kapadiya.posts.list.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.krunal3kapadiya.posts.common.dao.CommentsDao
import com.krunal3kapadiya.posts.common.dao.PostsDao
import com.krunal3kapadiya.posts.common.dao.UsersDao

/**
 * Created by Krunal on 12/22/2017.
 */
class ListViewModelFactory(private val dataSource: Any, private val usersDao: UsersDao, private val commentsDao: CommentsDao) : ViewModelProvider.Factory {

    /**
     * providing list view model
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(dataSource as PostsDao, usersDao, commentsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}