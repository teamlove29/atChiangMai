package com.alw.atchiangmai.api

import com.google.gson.annotations.SerializedName

data class Album(
    val userId: Int,
    val id:Int,
    @SerializedName("title")
    val album_name :String
)