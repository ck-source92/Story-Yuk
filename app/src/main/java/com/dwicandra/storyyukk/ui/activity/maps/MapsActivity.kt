package com.dwicandra.storyyukk.ui.activity.maps

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dwicandra.storyyukk.R
import com.dwicandra.storyyukk.data.result.ResultState
import com.dwicandra.storyyukk.databinding.ActivityMapsBinding
import com.dwicandra.storyyukk.ui.activity.main.HomeViewModelFactory
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val mapsViewModel by viewModels<MapsViewModel> {
        HomeViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupViewModel()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mapsViewModel.allStoriesLocation.observe(this) {
            when (it) {
                is ResultState.Success -> {
                    for (place in it.data) {
                        val latLng = LatLng(place.lat, place.lon)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(place.name)
                                .snippet(place.description)
                        )
                    }
                    val jkt = LatLng(-6.200000, 106.816666)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(jkt))
                }
                is ResultState.Loading -> {
                    showSnackBar(binding.root, "Loading")
                }
                is ResultState.Error -> {
                    showSnackBar(binding.root, "Error")
                }
            }
        }
    }

    private fun setupViewModel() {
        mapsViewModel.getAllStoriesLocation()
    }

    private fun showSnackBar(view: View, message: String) {
        val snack = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        val snackBarView = snack.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow_700))
        snack.show()

    }
}