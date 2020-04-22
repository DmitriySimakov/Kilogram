package com.dmitrysimakov.kilogram.ui.map

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dmitrysimakov.kilogram.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val REQUEST_LOCATION_PERMISSION = 1
private const val DEFAULT_ZOOM = 15f

class MapFragment : Fragment(), OnMapReadyCallback {
    
    private lateinit var map: GoogleMap
    
    private lateinit var locationClient: FusedLocationProviderClient
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        enableMyLocation()
    }
    
    private fun enableMyLocation() {
        val locationPermission = ContextCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
        )
        if (locationPermission  == PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
            moveToMyLocation()
        } else {
            requestPermissions(arrayOf(ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        }
    }
    
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
    
    private fun moveToMyLocation() = lifecycleScope.launch {
        val location = locationClient.lastLocation.await()
        if (location != null) {
            val latLng = LatLng(location.latitude, location.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM))
        }
    }
}
