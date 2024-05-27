package com.appinventiv.kotlinbasics

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("quote", alternate = ["message"])
    val quote: String? = null
)
