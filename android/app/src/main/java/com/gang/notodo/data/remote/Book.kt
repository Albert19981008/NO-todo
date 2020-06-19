package com.gang.notodo.data.remote

import com.google.gson.annotations.SerializedName


data class Book(
    @SerializedName("book_name")
    val bookName: String,
    @SerializedName("author")
    val author: String
)