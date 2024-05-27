package com.appinventiv.kotlinbasics

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("quotes/random")
    suspend fun getRandomQuote() : Response<Quote>

    @GET("quotes/{id}")
    suspend fun getSingleQuote(@Path("id") id: String) : Response<Quote>

}