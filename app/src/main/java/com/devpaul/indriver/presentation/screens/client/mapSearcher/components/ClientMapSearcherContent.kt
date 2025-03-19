package com.devpaul.indriver.presentation.screens.client.mapSearcher.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devpaul.indriver.presentation.screens.client.ClientMapSearcherViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import timber.log.Timber

@Composable
fun ClientMapSearcherContent(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    vm: ClientMapSearcherViewModel = hiltViewModel(),
) {

    vm.location.let { position ->
        Timber.d("PositionScreen: $position")
    }

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-12.2303334, -76.9328205), 10f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        cameraPositionState = cameraPosition,
        properties = MapProperties(isMyLocationEnabled = true)
    )
}