package com.krunal3kapadiya.posts.network

import com.krunal3kapadiya.posts.common.models.Comments
import com.krunal3kapadiya.posts.common.models.Posts
import com.krunal3kapadiya.posts.common.models.Users
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * list of network calls
 */
interface PostApi {

    /**
     * posts data from api
     */
    @GET("/posts")
    fun getPosts(): Observable<List<Posts>>

    /**
     * users data from api
     */
    @GET("/users")
    fun getUsers(): Observable<List<Users>>

    /**
     * comments data from api
     */
    @GET("/comments")
    fun getComments(): Observable<List<Comments>>
}