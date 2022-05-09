package com.errorsmasher.mytask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.errorsmasher.mytask.databinding.ActivityRetrofitBinding

class RetrofitActivity : AppCompatActivity() {
    lateinit var binding: ActivityRetrofitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}