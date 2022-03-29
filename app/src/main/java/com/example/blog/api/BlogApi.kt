package com.example.blog.api

import com.example.blog.models.Post
import com.example.blog.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BlogApi {

    @GET("posts")
    suspend fun getPost(@Query("_page")page :Int = 1,@Query("_limit") limit :Int =10):List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId:Int):Post

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): User

    @GET("posts/{id}")
     fun getPostViaCallBack(@Path("id") postId:Int):Call<Post>

    @GET("users/{id}")
     fun getUserViaCallBack(@Path("id") userId: Int): Call<User>
}