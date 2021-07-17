package com.adit.poskoapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.adit.poskoapp.databinding.ActivityLocationPickerBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_location_picker.*

@Suppress("DEPRECATION")
class LocationPickerActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private lateinit var binding : ActivityLocationPickerBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var myLocation : Location
    private lateinit var autocompleteFragment : PlaceAutocompleteFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            googleMap?.setOnMapClickListener(this@LocationPickerActivity)
        }

        searchBox()
    }

    override fun onMapClick(p0: LatLng) {
        val marker = MarkerOptions().position(LatLng(p0!!.latitude, p0.longitude))
        googleMap.clear()
        googleMap.addMarker(marker)

        println("PICK LAT " + p0.latitude)
        println("PICK LONG " + p0.longitude)
        val returnIntent = Intent(this@LocationPickerActivity, CreateOrUpdatePoskoActivity::class.java).apply {
            putExtra("LATITUDE", p0.latitude.toString())
            putExtra("LONGITUDE", p0.longitude.toString())
        }
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun searchBox(){
        autocompleteFragment = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment;
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener{
            override fun onPlaceSelected(p0: Place?) {
                googleMap.clear()
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(p0?.latLng))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p0?.latLng, 15f))
                println("Place Selected : "+ p0?.name)
            }

            override fun onError(p0: Status?) {
                println("STATUS ERROR " + p0)
                Toast.makeText(this@LocationPickerActivity, "Tidak bisa mencari lokasi", Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun onMapReady(p0: GoogleMap) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 666)
        }else{
            val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@LocationPickerActivity)
            fusedLocationProviderClient.lastLocation.apply {
                addOnCompleteListener{
                    if(it.isSuccessful){
                        it.result?.let{
                            myLocation = it
                            googleMap = p0
                            val point = LatLng(myLocation.latitude, myLocation.longitude)
                            googleMap.apply {
                                uiSettings?.isZoomControlsEnabled = true
                                uiSettings?.isMyLocationButtonEnabled = true
                                uiSettings?.isTiltGesturesEnabled = true
                                animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.Builder().target(point).zoom(15f).build()))
                                isMyLocationEnabled  = true
                                googleMap?.setOnMapClickListener(this@LocationPickerActivity)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 666){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enable location permission", Toast.LENGTH_SHORT).show()
            }
        }
    }
}