package com.errorsmasher.mytask.comman

import retrofit.Call
import retrofit.http.Field
import retrofit.http.FormUrlEncoded
import retrofit.http.Header
import retrofit.http.POST

interface TestApi {
    @FormUrlEncoded
    @POST("https://www.maishainfotech.com/interview/fetchdata.php")
    fun getRetroFitResponce(
        @Header("Authorization") authHeader: String,
        @Field("email") userEmail: String
    ): Call<EarningModal>
}