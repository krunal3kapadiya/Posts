package com.krunal3kapadiya.posts.common.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import com.krunal3kapadiya.posts.common.models.Posts

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Posts>)
}