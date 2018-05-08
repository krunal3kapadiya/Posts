package com.krunal3kapadiya.posts.list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.common.base.BaseActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.krunal3kapadiya.posts.Injection
import com.krunal3kapadiya.posts.R
import com.krunal3kapadiya.posts.common.models.Comments
import com.krunal3kapadiya.posts.common.models.PostUserComments
import com.krunal3kapadiya.posts.common.models.Posts
import com.krunal3kapadiya.posts.common.models.Users
import com.krunal3kapadiya.posts.detail.DetailActivity
import com.krunal3kapadiya.posts.list.viewmodel.ListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader

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

        val listPosts = getPostList()
        disposable.add(listViewModel.addOrUpdatePosts(listPosts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )

        val listComments = getCommentsList()
        disposable.add(listViewModel.addOrUpdateComments(listComments)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())

        val listUsers = getUsersList()
        disposable.add(listViewModel.addOrUpdateUsers(listUsers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())


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

    // TODO remove below 3 methods
    private fun getPostList(): List<Posts> {
        val builder = StringBuilder()
        val data = resources.openRawResource(R.raw.posts)
        val reader = BufferedReader(InputStreamReader(data) as Reader?)
        var line: String? = null
        do {
            line = reader.readLine()
            line?.let { builder.append(line) }
        } while (line != null)
        //Parse resource into key/values
        val rawJson = builder.toString()
        val listType = object : TypeToken<List<Posts>>() {}.type
        return Gson().fromJson(rawJson, listType)
    }

    private fun getUsersList(): List<Users> {
        val builder = StringBuilder()
        val data = resources.openRawResource(R.raw.users)
        val reader = BufferedReader(InputStreamReader(data) as Reader?)
        var line: String? = null
        do {
            line = reader.readLine()
            line?.let { builder.append(line) }
        } while (line != null)
        //Parse resource into key/values
        val rawJson = builder.toString()
        val listType = object : TypeToken<List<Users>>() {}.type
        return Gson().fromJson(rawJson, listType)
    }

    private fun getCommentsList(): List<Comments> {
        val builder = StringBuilder()
        val data = resources.openRawResource(R.raw.comments)
        val reader = BufferedReader(InputStreamReader(data) as Reader?)
        var line: String? = null
        do {
            line = reader.readLine()
            line?.let { builder.append(line) }
        } while (line != null)
        //Parse resource into key/values
        val rawJson = builder.toString()
        val listType = object : TypeToken<List<Comments>>() {}.type
        return Gson().fromJson(rawJson, listType)
    }
}