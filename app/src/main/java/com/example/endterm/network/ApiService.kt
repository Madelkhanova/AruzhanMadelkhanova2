package com.example.endterm.network


import com.example.endterm.ToDo
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("todos")
    fun getTodos(): Call<List<ToDo>>

    @POST("todos")
    fun postTodos(@Body body: JsonObject): Call<JsonObject>

    @GET("todos/{id}")
    fun getTodoById(@Path("id") todoId: Int): Call<ToDo>

}