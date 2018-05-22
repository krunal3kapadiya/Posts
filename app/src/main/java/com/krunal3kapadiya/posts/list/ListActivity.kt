package com.krunal3kapadiya.posts.list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.krunal3kapadiya.posts.Injection
import com.krunal3kapadiya.posts.R
import com.krunal3kapadiya.posts.common.models.PostUserComments
import com.krunal3kapadiya.posts.detail.DetailActivity
import com.krunal3kapadiya.posts.list.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_list.*

/**
 * first activity of the app
 */

class ListActivity : AppCompatActivity(), ListAdapter.onListItemClick {

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
        val viewModelFactory = Injection.provideListViewModel(this)
        listViewModel = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel::class.java)

        //get the total number of comments(in list)
        postUserComments = ArrayList()
        adapter = ListAdapter(this, postUserComments)

        rvPosts.layoutManager = LinearLayoutManager(this)
        rvPosts.adapter = adapter

        // on swipe fetch and display data
        swipePosts.setOnRefreshListener({
            listViewModel.fetchAndDisplayData()
            swipePosts.isRefreshing = false
        })

        // initially fetch and display data
        listViewModel.fetchAndDisplayData()

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
}