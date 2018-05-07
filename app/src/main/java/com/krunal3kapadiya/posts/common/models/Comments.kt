package com.krunal3kapadiya.posts.common.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity()
data class Comments(@SerializedName("postId") val postId: Int,
                    @SerializedName("id") @PrimaryKey val id: Int,
                    @SerializedName("name") val name: String,
                    @SerializedName("email") val email: String,
                    @SerializedName("body") val body: String)