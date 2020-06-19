package com.gang.notodo.data.remote.network

import com.gang.notodo.data.remote.Book
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface NetworkApi {

    @GET("/books")
    fun getBook(): Call<List<Book>>
}