package com.gang.notodo.remote.network

import com.gang.notodo.remote.Book
import retrofit2.Call
import retrofit2.http.GET

interface NetworkApi {

    @GET("/books")
    fun getBook(): Call<List<Book>>
}