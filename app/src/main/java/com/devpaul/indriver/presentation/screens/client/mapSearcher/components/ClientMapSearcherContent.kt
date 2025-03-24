package com.devpaul.indriver.presentation.screens.client.mapSearcher.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devpaul.indriver.R
import com.devpaul.indriver.presentation.components.DefaultTextField
import com.devpaul.indriver.presentation.screens.client.ClientMapSearcherViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientMapSearcherContent(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    vm: ClientMapSearcherViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    var query by remember { mutableStateOf("") }
    val placePredictions by vm.placePredictions.collectAsState()
    val selectedPlace by vm.selectedPlace.collectAsState()
    val location by vm.location.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    var isCameraCentered by remember {
        mutableStateOf(false)
    }

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style),
                isMyLocationEnabled = true,
            )
        )
    }

    LaunchedEffect(key1 = location) {
        if (location != null && !isCameraCentered) {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                location!!, 14f
            )
            isCameraCentered = true
        }
    }

    BottomSheetScaffold(
        sheetContent = {
            AnimatedVisibility(visible = !vm.isInteractingWithMap) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(calculateSheetHeight(vm = vm))
                        .background(Color.White)
                ) {
                    DefaultTextField(
                        modifier = Modifier,
                        value = query,
                        label = "Search",
                        icon = Icons.Default.LocationOn,
                        onValueChange = {
                            query = it
                            vm.getPlacePredictions(it)
                        },
                    )
                    LazyColumn {
                        items(placePredictions) { prediction ->
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        vm.getPlaceDetails(prediction.placeId)
                                    },
                                text = prediction.fullText,
                            )
                        }
                    }

                    selectedPlace?.let { place ->
                        Text(
                            text = "Selected place: ${place.name} ${place.latLng}",
                        )
                    }
                }
            }
        },
        scaffoldState = rememberBottomSheetScaffoldState(),
        sheetPeekHeight = calculateSheetHeight(vm),
        content = {
            Box(
                modifier = Modifier.fillMaxHeight(
                    fraction = if (vm.isInteractingWithMap) 0.95f else 0.6f
                )
            ) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp),
                    cameraPositionState = cameraPositionState,
                    properties = mapProperties
                ) {
                    location?.let { position ->
                        Timber.d("PositionScreen: $position")
                        Marker(
                            state = MarkerState(position = position)
                        )
                    }
                }
                CheckForMapInteraction(cameraPositionState = cameraPositionState, vm = vm)
            }
        }
    )
}

@Composable
private fun calculateSheetHeight(vm: ClientMapSearcherViewModel): Dp {
    val normalHeight = LocalConfiguration.current.screenHeightDp.dp * 0.4f
    val minimizedHeight = 50.dp
    return animateDpAsState(
        if (vm.isInteractingWithMap) {
            minimizedHeight
        } else {
            normalHeight
        },
        animationSpec = spring(stiffness = 30f)
    ).value
}

@Composable
private fun CheckForMapInteraction(
    cameraPositionState: CameraPositionState,
    vm: ClientMapSearcherViewModel,
) {
    var initialCameraPosition by remember { mutableStateOf(cameraPositionState.position) }

    val onMapCameraMoveStart: (cameraPosition: CameraPosition) -> Unit = { newPosition ->
        initialCameraPosition = newPosition
        vm.isInteractingWithMap = true
    }

    val onMapCameraIdle: (cameraPosition: CameraPosition) -> Unit = { newCameraPosition ->
        val cameraMovementReason = cameraPositionState.cameraMoveStartedReason
        if (newCameraPosition.zoom < initialCameraPosition.zoom) {
            vm.isInteractingWithMap = false
        }
        if (newCameraPosition.zoom > initialCameraPosition.zoom) {
            vm.isInteractingWithMap = false
        }
        if (newCameraPosition.bearing != initialCameraPosition.bearing) {
            vm.isInteractingWithMap = false
        }
        if (cameraMovementReason == CameraMoveStartedReason.GESTURE && newCameraPosition.target != initialCameraPosition.target) {
            vm.isInteractingWithMap = false
        }
        initialCameraPosition = newCameraPosition
    }

    LaunchedEffect(key1 = cameraPositionState.isMoving) {
        if (cameraPositionState.isMoving) {
            onMapCameraMoveStart(cameraPositionState.position)
        } else {
            onMapCameraIdle(cameraPositionState.position)
        }
    }

}