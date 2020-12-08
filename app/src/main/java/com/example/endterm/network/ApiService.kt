package com.example.endterm.network


import com.example.endterm.Comment
import com.example.endterm.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("posts/")
    fun getTodos(): Call<List<Post>>

    @GET("posts/{id}/")
    fun getTodoById(@Path("id") todoId: Int): Call<Post>

    @GET("comments")
    fun getToIdComment(@Query("postId") todoId: Int): Call<List<Comment>>
}