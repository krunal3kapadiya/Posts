package com.krunal3kapadiya.posts.detail.comments

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krunal3kapadiya.posts.R
import com.krunal3kapadiya.posts.common.models.Comments
import kotlinx.android.synthetic.main.row_comments.view.*

class CommentsAdapter(
        val context: Context,
        private val comments: List<Comments>
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(
                R.layout.row_comments,
                parent,
                false
        ))
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(comments[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(comments: Comments) {
            with(comments) {
                itemView.txtCommentsName.text = comments.name
                itemView.txtCommentsEmail.text = comments.email
                itemView.txtCommentsBody.text = comments.body
            }
        }
    }
}