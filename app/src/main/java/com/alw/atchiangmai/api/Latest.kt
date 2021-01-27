package com.alw.atchiangmai.api


import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class Latest(
    val base : String,
    val date : String,
    val rates : JsonObject,
//    val rateBase : String,
//    val rateSub : String
)