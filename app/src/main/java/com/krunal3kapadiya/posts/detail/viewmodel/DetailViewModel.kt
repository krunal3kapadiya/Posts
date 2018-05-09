package com.krunal3kapadiya.posts.detail.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.krunal3kapadiya.posts.common.dao.CommentsDao
import com.krunal3kapadiya.posts.common.models.Comments
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class DetailViewModel(private var commentsDao: CommentsDao) : ViewModel() {

    private val disposable = CompositeDisposable()

    // list of comment by postId
    private var commentList: MediatorLiveData<List<Comments>> = MediatorLiveData()

    /**
     * fetching comment by id to display in detail screen
     */
    fun getCommentById(postId: Int): LiveData<List<Comments>> {
        disposable.addAll(Completable.create { e ->
            commentList.addSource(commentsDao.getCommentById(postId), { comments ->
                if (commentList.getValue() != null) {
                    val listData: List<Comments> = commentList.getValue()!!
                    (listData as ArrayList).clear()
                    listData.addAll((comments as ArrayList))
                    commentList.postValue(listData)
                } else {
                    commentList.postValue(comments)
                }
            })
            e.onComplete()
        }.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())

        return commentList
    }
}