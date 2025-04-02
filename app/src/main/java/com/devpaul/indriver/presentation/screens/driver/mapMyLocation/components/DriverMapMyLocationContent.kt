package com.devpaul.indriver.presentation.screens.driver.mapMyLocation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devpaul.indriver.R
import com.devpaul.indriver.presentation.components.DefaultButton
import com.devpaul.indriver.presentation.screens.driver.DriverMyLocationViewModel
import com.devpaul.indriver.presentation.util.bitmapDescriptorFromVector
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DriverMapMyLocationContent(
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    vm: DriverMyLocationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val location by vm.location.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    var isCameraCentered by remember { mutableStateOf(false) }
    var myLocationMarkerDescriptor by remember { mutableStateOf<BitmapDescriptor?>(null) }
    var isMapReady by remember { mutableStateOf(false) }
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style),
                isMyLocationEnabled = false
            )
        )
    }

    LaunchedEffect(key1 = isMapReady) {
        if (isMapReady) {
            myLocationMarkerDescriptor =
                context.bitmapDescriptorFromVector(R.drawable.icon_taxi, 130, 193)
        }
    }

    LaunchedEffect(key1 = location) {
        if (location != null && !isCameraCentered) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(location!!, 14f)
            isCameraCentered = true
        }
    }

    Box {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(), cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onMapLoaded = {
                isMapReady = true
            }
        ) {
            location.let { position ->
                if (position != null) {
                    Marker(
                        state = MarkerState(position = position),
                        icon = myLocationMarkerDescriptor
                    )
                }

            }
        }
        DefaultButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = if (vm.isSocketConnected) "Desconectarse" else "Conectarse",
            onClick = {
                if (vm.isSocketConnected) {
                    vm.disconnectSocket()
                } else {
                    vm.connectSocket()
                }
            },
        )
    }

}