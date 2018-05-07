package com.krunal3kapadiya.posts.common.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import com.krunal3kapadiya.posts.common.models.Comments

@Dao
interface CommentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comments: List<Comments>)
}