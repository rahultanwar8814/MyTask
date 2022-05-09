package com.errorsmasher.mytask.comman

import com.squareup.okhttp.Credentials

object Constant {
    val BasicAuth = "interviewmaisha" + ":" + "interview@maisha@12701"
    var basic = Credentials.basic("interviewmaisha", "interview@maisha@12701")
    var AUTH = "interviewmaisha:interview@maisha@12701"
    // private val AUTH = "Basic" + Base64.encodeToString("interviewmaisha:interview@maisha@12701".toByteArray(),Base64.NO_WRAP)
}