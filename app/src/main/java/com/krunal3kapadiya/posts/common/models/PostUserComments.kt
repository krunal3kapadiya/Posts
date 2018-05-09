package com.krunal3kapadiya.posts.common.models

import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

data class PostUserComments(@PrimaryKey val postId: Int,
                            val postTitle: String,
                            val postBody: String,
                            val userName: String,
                            val comments: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(postId)
        parcel.writeString(postTitle)
        parcel.writeString(postBody)
        parcel.writeString(userName)
        parcel.writeInt(comments)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostUserComments> {
        override fun createFromParcel(parcel: Parcel): PostUserComments {
            return PostUserComments(parcel)
        }

        override fun newArray(size: Int): Array<PostUserComments?> {
            return arrayOfNulls(size)
        }
    }

}