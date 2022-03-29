package com.example.blog.api

import com.example.blog.models.Post
import com.example.blog.models.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BlogApi {

    @GET("posts")
    suspend fun getPost():List<Post>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId:Int):Post

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): User

}