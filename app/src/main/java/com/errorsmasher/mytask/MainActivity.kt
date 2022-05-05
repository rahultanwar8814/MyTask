package com.errorsmasher.mytask

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    private var client: GoogleSignInClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myRef = database.getReference("message")
        myRef.setValue("Hello, World!")
    }

    fun login(view: View) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("956918141582-bi9n0u8uqdlkpqa760r9ab0dg9dmhrdb.apps.googleusercontent.com")
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(this, gso)
    }
}