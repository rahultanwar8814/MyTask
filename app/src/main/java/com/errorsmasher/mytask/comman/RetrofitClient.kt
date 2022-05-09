package com.errorsmasher.mytask.comman

import com.squareup.okhttp.OkHttpClient
import retrofit.GsonConverterFactory
import retrofit.Retrofit


object RetrofitClient {
    private val retrofitClient = Retrofit.Builder()
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://www.maishainfotech.com/interview/fetchdata.php").build()

    private val TEST_API: TestApi = retrofitClient.create(TestApi::class.java)

    fun getApi(): TestApi {
        return TEST_API
    }
}