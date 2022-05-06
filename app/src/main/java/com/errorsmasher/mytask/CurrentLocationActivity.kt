package com.errorsmasher.mytask

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.errorsmasher.mytask.databinding.ActivityCurrentLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CurrentLocationActivity : AppCompatActivity() {
    lateinit var binding: ActivityCurrentLocationBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val database = Firebase.database


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentLocationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val myRef = database.getReference("Live Location")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission(myRef)

    }
    fun checkLocationPermission(myRef: DatabaseReference) {
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101)

            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                myRef.setValue("latitude:${it.longitude} longitude:${it.longitude}")
                Toast.makeText(applicationContext,
                    "${it.latitude} ${it.longitude}",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

}