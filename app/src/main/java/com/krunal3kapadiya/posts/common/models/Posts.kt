package com.krunal3kapadiya.posts.common.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")
data class Posts(@SerializedName("userId") val userId: Int,
                 @SerializedName("id") @PrimaryKey val postId: Int,
                 @SerializedName("title") val postTitle: String,
                 @SerializedName("body") val postBody: String)