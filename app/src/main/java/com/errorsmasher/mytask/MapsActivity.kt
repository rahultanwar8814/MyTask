package com.errorsmasher.mytask

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.errorsmasher.mytask.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapsBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val database = Firebase.database
    var currentLocation: Location? = null
    val PERMISSION_ID = 1010

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myRef = database.getReference("Live Location")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission(myRef)
        val kk = isGPSEnabled()
        Log.d("qqqqqq",kk.toString())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
        val markOptionM = MarkerOptions().position(sydney).title("I am Here")
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
        googleMap.addMarker(markOptionM)
    }

    fun checkLocationPermission(myRef: DatabaseReference) {
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_ID)

            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                setTitle("${it.latitude}, ${it.longitude}")
                currentLocation = it
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
                myRef.setValue("latitude:${it.longitude} longitude:${it.longitude}")
                Toast.makeText(applicationContext,
                    "${it.latitude} ${it.longitude}",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

//    fun checkPermissions(): Boolean {
//        if (ActivityCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//            && ActivityCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            return true
//        }
//        return false
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val myRef = database.getReference("Live Location")
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                checkLocationPermission(myRef)
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_ID)
            }
        }
    }
    private fun isGPSEnabled(): Boolean {
        var locationManager: LocationManager? = null
        var isEnabled = false
        if (locationManager == null) {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        return isEnabled
    }
}