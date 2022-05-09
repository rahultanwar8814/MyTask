package com.errorsmasher.mytask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.errorsmasher.mytask.comman.Constant.basic
import com.errorsmasher.mytask.comman.ResponcModal
import com.errorsmasher.mytask.comman.RetrofitClient
import com.errorsmasher.mytask.databinding.ActivityRetrofitBinding
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import java.util.*

class RetrofitActivity : AppCompatActivity() {
    lateinit var binding: ActivityRetrofitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.retroFit.setOnClickListener {
            val authHeader = "Basic+$basic "
            val callBack = RetrofitClient.getApi()
                .getRetroFitResponce(authHeader, "interview@maisha@12701")
            callBack.enqueue(object : Callback<ResponcModal> {
                override fun onResponse(
                    response: Response<ResponcModal>?,
                    retrofit: Retrofit?
                ) {
                    if (response!!.isSuccess) {
                        Log.d("qqqqq", "Successful")
                    } else {
                        Log.d("qqqqq", "Failed")
                    }
                }

                override fun onFailure(t: Throwable?) {
                    Log.d("qqqqq", t.toString())
                }
            })
        }
    }
}