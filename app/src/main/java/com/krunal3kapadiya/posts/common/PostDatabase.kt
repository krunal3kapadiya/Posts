package com.krunal3kapadiya.posts.common

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.krunal3kapadiya.posts.common.dao.CommentsDao
import com.krunal3kapadiya.posts.common.dao.PostsDao
import com.krunal3kapadiya.posts.common.dao.UsersDao
import com.krunal3kapadiya.posts.common.models.Comments
import com.krunal3kapadiya.posts.common.models.Posts
import com.krunal3kapadiya.posts.common.models.Users

@Database(entities = arrayOf(Posts::class, Users::class, Comments::class), version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
    abstract fun usersDao(): UsersDao
    abstract fun commentsDao(): CommentsDao

    companion object {
        @Volatile
        private var INSTANCE: PostDatabase? = null

        /**
         * fetching single instance of post database
         */
        fun getInstance(context: Context): PostDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        /**
         * creating database
         */
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                PostDatabase::class.java,
                "posts.db")
                .build()
    }
}