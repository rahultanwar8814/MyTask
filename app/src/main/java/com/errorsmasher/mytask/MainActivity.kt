package com.errorsmasher.mytask

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.errorsmasher.mytask.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task

const val RC_SIGN_IN = 123

class MainActivity : AppCompatActivity() {
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("956918141582-bi9n0u8uqdlkpqa760r9ab0dg9dmhrdb.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            binding.login.visibility = View.GONE
            binding.name.visibility = View.VISIBLE
            binding.name.text = acct.displayName
        }
        binding.login.setOnClickListener {
            val signInIntent = mGoogleSignInClient?.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        binding.getLoc.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java).apply {
            }
            startActivity(intent)
        }
        binding.onGps.setOnClickListener {
            requestDeviceLocationSettings()
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            binding.login.visibility = View.GONE
            binding.name.visibility = View.VISIBLE
            binding.name.text = account.displayName
        } catch (e: ApiException) {
            binding.login.visibility = View.VISIBLE
            binding.name.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    fun requestDeviceLocationSettings() {
        val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            // ...
            val state = locationSettingsResponse.locationSettingsStates
            val label =
                "GPS >> (Present: ${state?.isGpsPresent}  | Usable: ${state?.isGpsUsable} ) \n\n" +
                        "Network >> ( Present: ${state?.isNetworkLocationPresent} | Usable: ${state?.isNetworkLocationUsable} ) \n\n" +
                        "Location >> ( Present: ${state?.isLocationPresent} | Usable: ${state?.isLocationUsable} )"

            showToast(label)

            binding.textView.text = label
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(
                        this@MainActivity,
                        100
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                    binding.textView.text = sendEx.message.toString()
                }
            }
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG).show()
    }
}