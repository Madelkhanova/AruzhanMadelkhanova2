package com.example.endterm


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Post
    (
    @SerializedName("userId")
    var userId: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("body")
    var body: String,
) : Serializable