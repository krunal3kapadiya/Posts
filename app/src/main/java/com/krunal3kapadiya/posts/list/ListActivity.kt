package com.krunal3kapadiya.posts.list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.common.base.BaseActivity
import com.krunal3kapadiya.posts.Injection
import com.krunal3kapadiya.posts.R
import com.krunal3kapadiya.posts.common.models.Comments
import com.krunal3kapadiya.posts.common.models.PostUserComments
import com.krunal3kapadiya.posts.common.models.Posts
import com.krunal3kapadiya.posts.common.models.Users
import com.krunal3kapadiya.posts.detail.DetailActivity
import com.krunal3kapadiya.posts.list.viewmodel.ListViewModel
import com.krunal3kapadiya.posts.network.NetworkModule
import com.krunal3kapadiya.posts.network.PostApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list.*

/**
 * first activity of the app
 */

class ListActivity : BaseActivity(), ListAdapter.onListItemClick {

    private lateinit var disposable: CompositeDisposable

    /**
     * on create initialize needed variables
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //initializing
        disposable = CompositeDisposable()
        val viewModelFactory = Injection.provideListViewModel(this)
        val listViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)

        // post api object
        val postApi: PostApi = NetworkModule.providePostsApi()

        // post observer
        val postObservable: Observable<List<Posts>> = postApi.getPosts()
        postObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    disposable.add(listViewModel.addOrUpdatePosts(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())
                })

        // user observer
        val userObservable: Observable<List<Users>> = postApi.getUsers()
        userObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    disposable.add(listViewModel.addOrUpdateUsers(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())
                })

        // comment observer
        val commentObservable: Observable<List<Comments>> = postApi.getComments()
        commentObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    disposable.add(listViewModel.addOrUpdateComments(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())
                })


        //get the total number of comments(in list)
        val postUserComments: List<PostUserComments> = ArrayList()
        val adapter = ListAdapter(this, postUserComments)
        listViewModel.getListData().observe(this, android.arch.lifecycle.Observer { postUserCommentsList ->
            (postUserComments as ArrayList<PostUserComments>).clear()
            postUserComments.addAll(postUserCommentsList as ArrayList)
            adapter.notifyDataSetChanged()
        })

        rvPosts.layoutManager = LinearLayoutManager(this)
        rvPosts.adapter = adapter
    }

    /**
     * on list clicked open detail of content
     * open new screen
     */
    override fun onListClicked(posts: PostUserComments) {
        DetailActivity.launchActivity(this, posts)
    }

    /**
     * clear disposable
     */
    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}