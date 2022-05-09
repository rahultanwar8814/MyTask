package com.errorsmasher.mytask.comman

import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("fname")
    lateinit var fname: String
    @SerializedName("lname")
    lateinit var lname: String
    @SerializedName("mobile")
    lateinit var mobile: String
    @SerializedName("email")
    lateinit var email: String
    @SerializedName("address")
    lateinit var address: String
    @SerializedName("dor")
    lateinit var dor: String
}