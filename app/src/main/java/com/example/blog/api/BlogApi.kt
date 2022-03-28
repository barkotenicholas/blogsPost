package com.example.blog.api

import com.example.blog.models.Post
import retrofit2.http.GET

interface BlogApi {

    @GET("posts")
    suspend fun getPost():List<Post>

}