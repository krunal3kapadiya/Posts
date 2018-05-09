package com.krunal3kapadiya.posts.common.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.krunal3kapadiya.posts.common.models.Comments

@Dao
interface CommentsDao {
    /**
     * insertion
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comments: List<Comments>)

    /**
     * fetch all comments
     */
    @Query("SELECT * FROM comments")
    fun getAll(): LiveData<List<Comments>>

    /**
     * fetch comment by ID
     */
    @Query("SELECT * FROM comments WHERE postId = :postId")
    fun getCommentById(postId: Int): LiveData<List<Comments>>
}