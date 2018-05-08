package com.krunal3kapadiya.posts.common.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.krunal3kapadiya.posts.common.models.PostUserComments
import com.krunal3kapadiya.posts.common.models.Posts
import io.reactivex.Flowable

@Dao
interface PostsDao {
    /**
     * insertion
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Posts>)

    /**
     * get list data
     * this data is displayed in main screen
     */
    @Query("SELECT  posts.postId AS postId, posts.postTitle AS postTitle ,posts.postBody AS postBody, users.userName as userName, COUNT(comments.id) as comments FROM posts JOIN users ON users.id=posts.userId JOIN comments ON comments.postId=posts.postId GROUP BY posts.postId HAVING posts.userId= users.id AND posts.postId = comments.postId")
    fun getListData(): LiveData<List<PostUserComments>>
}