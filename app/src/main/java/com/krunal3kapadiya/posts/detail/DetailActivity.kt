package com.krunal3kapadiya.posts.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.krunal3kapadiya.posts.Injection
import com.krunal3kapadiya.posts.R
import com.krunal3kapadiya.posts.common.models.Comments
import com.krunal3kapadiya.posts.common.models.PostUserComments
import com.krunal3kapadiya.posts.detail.comments.CommentsAdapter
import com.krunal3kapadiya.posts.detail.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val ARG_POST_DETAIL = "postsdata"

        fun launchActivity(context: Context, postUserComments: PostUserComments) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ARG_POST_DETAIL, postUserComments)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val postUserComments = intent.getParcelableExtra<PostUserComments>(ARG_POST_DETAIL)

        title = postUserComments.postTitle

        txtUserName.text = postUserComments.userName
        txtPostBody.text = postUserComments.postBody

        val viewModelFactory = Injection.provideDetailViewModel(this@DetailActivity)
        val detailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)

        val mainList: List<Comments> = ArrayList()
        val commentAdapter = CommentsAdapter(this, mainList)
        rvComments.layoutManager = LinearLayoutManager(this@DetailActivity)
        rvComments.adapter = commentAdapter

        detailViewModel.getCommentById(postUserComments.postId).observe(this, Observer { comments ->
            (mainList as ArrayList).addAll(comments as ArrayList)
            commentAdapter.notifyDataSetChanged()
        })
    }
}