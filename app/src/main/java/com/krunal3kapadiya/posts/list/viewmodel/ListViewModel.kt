package com.krunal3kapadiya.posts.list.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.krunal3kapadiya.posts.common.dao.CommentsDao
import com.krunal3kapadiya.posts.common.dao.PostsDao
import com.krunal3kapadiya.posts.common.dao.UsersDao
import com.krunal3kapadiya.posts.common.models.Comments
import com.krunal3kapadiya.posts.common.models.PostUserComments
import com.krunal3kapadiya.posts.common.models.Posts
import com.krunal3kapadiya.posts.common.models.Users
import com.krunal3kapadiya.posts.network.NetworkModule
import com.krunal3kapadiya.posts.network.PostApi
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListViewModel(private val postData: PostsDao, private val usersDao: UsersDao, private val commentsDao: CommentsDao) : ViewModel() {

    private val LOG = ListViewModel::class.java.name
    private var postUserCommentsList: MediatorLiveData<List<PostUserComments>> = MediatorLiveData()
    private val disposable: CompositeDisposable = CompositeDisposable()

    /**
     * add and update posts
     */
    fun addOrUpdatePosts(posts: List<Posts>): Completable {
        return Completable.fromAction {
            postData.insert(posts = posts)
        }
    }

    /**
     * add update users
     * users name is used in list
     */
    fun addOrUpdateUsers(users: List<Users>): Completable {
        return Completable.fromAction {
            usersDao.insert(users)
        }
    }

    /**
     * add or update comments
     */
    fun addOrUpdateComments(comments: List<Comments>): Completable {
        return Completable.fromAction {
            commentsDao.insert(comments = comments)
        }
    }

    /**
     * this list will be display in main screen
     */
    fun getListData(): LiveData<List<PostUserComments>> {
        disposable.addAll(Completable.create { e ->
            postUserCommentsList.addSource(postData.getListData(), { postUserComments ->
                if (postUserCommentsList.value != null) {
                    val listData: List<PostUserComments> = postUserCommentsList.getValue()!!
                    (listData as ArrayList).clear()
                    listData.addAll((postUserComments as ArrayList))
                    postUserCommentsList.postValue(listData)
                } else {
                    postUserCommentsList.postValue(postUserComments)
                }
            })
            e.onComplete()
        }.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())

        return postUserCommentsList
    }

    /**
     * initially it will load the data
     * and in swipe refresh event also
     */
    fun fetchAndDisplayData() {
        // post api object
        val postApi: PostApi = NetworkModule.providePostsApi()

        // post observer
        val postObservable: Observable<List<Posts>> = postApi.getPosts()
        postObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    disposable.add(addOrUpdatePosts(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())
                }, { Log.e(LOG, "Something went wrong") })

        // user observer
        val userObservable: Observable<List<Users>> = postApi.getUsers()
        userObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    disposable.add(addOrUpdateUsers(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())
                }, { Log.e(LOG, "Something went wrong") })

        // comment observer
        val commentObservable: Observable<List<Comments>> = postApi.getComments()
        commentObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    disposable.add(addOrUpdateComments(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())
                }, { Log.e(LOG, "Something went wrong") })


    }
}