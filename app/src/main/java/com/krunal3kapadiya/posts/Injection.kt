package com.krunal3kapadiya.posts

import android.content.Context
import com.krunal3kapadiya.posts.common.PostDatabase
import com.krunal3kapadiya.posts.common.dao.CommentsDao
import com.krunal3kapadiya.posts.common.dao.PostsDao
import com.krunal3kapadiya.posts.common.dao.UsersDao
import com.krunal3kapadiya.posts.detail.viewmodel.DetailModelFactory
import com.krunal3kapadiya.posts.list.viewmodel.ListViewModelFactory

object Injection {
    /**
     * list view model
     */
    @JvmStatic
    fun provideListViewModel(context: Context): ListViewModelFactory {
        val postDao = providePostDataSource(context)
        val commentDao = provideCommentsDataSource(context)
        val userDao = provideUserDataSource(context)
        return ListViewModelFactory(postDao, userDao, commentDao)
    }

    /**
     * detail view model
     */
    fun provideDetailViewModel(context: Context): DetailModelFactory {
        val commentsDao = provideCommentsDataSource(context)
        return DetailModelFactory(commentsDao)
    }

    /**
     * post data source
     */
    fun providePostDataSource(context: Context): PostsDao {
        val database = PostDatabase.getInstance(context)
        return database.postsDao()
    }

    /**
     * user data source
     */
    fun provideUserDataSource(context: Context): UsersDao {
        val database = PostDatabase.getInstance(context)
        return database.usersDao()
    }

    /**
     * comment data source
     */
    fun provideCommentsDataSource(context: Context): CommentsDao {
        val database = PostDatabase.getInstance(context)
        return database.commentsDao()
    }
}
