package com.errorsmasher.mytask.comman

import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("fname")
    var fname: String? = null
    @SerializedName("lname")
    var lname: String? = null
    @SerializedName("mobile")
    var mobile: String? = null
    @SerializedName("email")
    var email: String? = null
    @SerializedName("address")
    var address: String? = null
    @SerializedName("dor")
    var dor: String? = null
}