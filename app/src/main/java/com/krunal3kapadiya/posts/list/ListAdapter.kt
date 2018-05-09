package com.krunal3kapadiya.posts.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krunal3kapadiya.posts.R
import com.krunal3kapadiya.posts.common.models.PostUserComments
import kotlinx.android.synthetic.main.row_lists.view.*

class ListAdapter(val context: Context, private val posts: List<PostUserComments>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var clickListener: onListItemClick = context as onListItemClick

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.row_lists, parent, false))
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(posts.get(position))

        holder?.itemView?.setOnClickListener({
            clickListener.onListClicked(posts.get(position))
        })

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(postUserComments: PostUserComments) {
            with(postUserComments) {
                itemView.txtTitle.text = postUserComments.postTitle
                itemView.txtBody.text = postUserComments.postBody
                itemView.txtUserName.text = postUserComments.userName
                itemView.txtNoComments.text = context.getString(R.string.total_comments, postUserComments.comments)
            }
        }
    }

    interface onListItemClick {
        fun onListClicked(posts: PostUserComments)
    }
}