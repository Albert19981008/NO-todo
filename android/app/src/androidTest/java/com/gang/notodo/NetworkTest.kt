package com.gang.notodo


import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gang.notodo.data.remote.Book
import com.gang.notodo.data.remote.network.NetworkApi
import com.gang.notodo.data.remote.network.NetworkConstants
import com.google.gson.Gson
import okhttp3.ResponseBody

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NetworkTest {
    @Test
    fun testNetWork() {
        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        val networkApi = retrofit.create(NetworkApi::class.java)
        networkApi.getBook().enqueue(object : retrofit2.Callback<List<Book>> {
            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.e("2222", "onFailure: failed$t")
            }

            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                Log.e("111111111111111111111", "onResponse: " + response.body()!!)
            }

        })
    }
}
