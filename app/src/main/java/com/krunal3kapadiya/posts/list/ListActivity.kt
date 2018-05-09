package com.krunal3kapadiya.posts.list

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.common.base.BaseActivity
import com.common.utils.Utils
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
    private val LOG = ListActivity::class.java.name

    lateinit var listViewModel: ListViewModel
    lateinit var postUserComments: List<PostUserComments>
    lateinit var adapter: ListAdapter

    /**
     * on create initialize needed variables
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        //initializing
        disposable = CompositeDisposable()
        val viewModelFactory = Injection.provideListViewModel(this)
        listViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)

        //get the total number of comments(in list)
        postUserComments = ArrayList()
        adapter = ListAdapter(this, postUserComments)

        rvPosts.layoutManager = LinearLayoutManager(this)
        rvPosts.adapter = adapter

        checkForInternet()

        // on swipe fetch and display data
        swipePosts.setOnRefreshListener({
            fetchAndDisplayData()
            swipePosts.isRefreshing = false
        })

        // initially fetch and display data
        fetchAndDisplayData()
    }

    /**
     * checking for the internet
     */
    private fun checkForInternet() {
        if (!Utils.isNetworkAvailable(this@ListActivity)) {
            Snackbar.make(swipePosts, getString(R.string.text_connect_internet), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.text_go_to_settings), {
                startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }).show()
        }
    }

    /**
     * initially it will load the data
     * and in swipe refresh event also
     */
    private fun fetchAndDisplayData() {
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
                }, { Log.e(LOG, getString(R.string.error_string)) })

        // user observer
        val userObservable: Observable<List<Users>> = postApi.getUsers()
        userObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    disposable.add(listViewModel.addOrUpdateUsers(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())
                }, { Log.e(LOG, getString(R.string.error_string)) })

        // comment observer
        val commentObservable: Observable<List<Comments>> = postApi.getComments()
        commentObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    disposable.add(listViewModel.addOrUpdateComments(it)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())
                }, { Log.e(LOG, getString(R.string.error_string)) })

        listViewModel.getListData().observe(this, android.arch.lifecycle.Observer { postUserCommentsList ->
            (postUserComments as ArrayList<PostUserComments>).clear()
            (postUserComments as ArrayList<PostUserComments>).addAll(postUserCommentsList as ArrayList)
            adapter.notifyDataSetChanged()
        })
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