package com.nakyoung.navermap

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nakyoung.navermap.ui.theme.NavermapTheme
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.*
import com.naver.maps.map.overlay.PolygonOverlay

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @OptIn(ExperimentalNaverMapApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this).apply {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity ,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions

            }
        }

        setContent {

            val cameraPositionState: CameraPositionState = rememberCameraPositionState()
            val myLocationMarkerState: MarkerState = rememberMarkerState()
            var myPolygonState by remember{ mutableStateOf(LatLng(0.0,0.0)) }

            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                val location: Location
                if (it == null) {
                    location = Location(null).apply {
                        latitude = 37.5666805
                        longitude = 126.9936
                    }
                } else {
                    location = it
                }

                cameraPositionState.position = CameraPosition(LatLng(location),17.0)
                myLocationMarkerState.position = LatLng(location)
                myPolygonState = LatLng(location)
            }

            NavermapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NaverMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ){
                        Marker(state = myLocationMarkerState,
                            captionText = "My"
                        )

                        PolygonOverlay(
                            outlineWidth = 3.dp,
                            coords = mutableListOf(myPolygonState, myPolygonState.offset(0.00001,0.00001), myPolygonState.offset(0.00000,0.00001)))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Preview
@Composable
fun MapPreview() {
    NaverMap(modifier = Modifier.fillMaxSize())
}