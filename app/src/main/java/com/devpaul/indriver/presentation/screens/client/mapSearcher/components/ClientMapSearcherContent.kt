package com.devpaul.indriver.presentation.screens.client.mapSearcher.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devpaul.indriver.R
import com.devpaul.indriver.presentation.components.DefaultTextField
import com.devpaul.indriver.presentation.screens.client.ClientMapSearcherViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.libraries.places.api.model.Place
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientMapSearcherContent(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    vm: ClientMapSearcherViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    var originQuery by remember { mutableStateOf("") }
    var destinationQuery by remember { mutableStateOf("") }
    val location by vm.location.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    var isOriginFocused by remember { mutableStateOf(false) }
    var isCameraCentered by remember { mutableStateOf(false) }
    var showSearchModal by remember { mutableStateOf(false) }
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
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isOriginFocused = true
                                showSearchModal = true
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color.DarkGray
                            )
                            Text(
                                text = if (originQuery.isEmpty()) "Recoger en" else originQuery,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .weight(1f),
                                color = Color.DarkGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isOriginFocused = false
                                showSearchModal = true
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color.DarkGray
                            )
                            Text(
                                text = if (destinationQuery.isEmpty()) "Destino" else destinationQuery,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .weight(1f),
                                color = Color.DarkGray
                            )
                        }
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
                        .padding(paddingValues),
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
                if (showSearchModal) {
                    PlaceSearchModal(
                        onDismissRequest = {
                            showSearchModal = false
                        },
                        onPlaceSelected = { place ->
                            if (isOriginFocused) {
                                originQuery = place.address
                            } else {
                                destinationQuery = place.address
                            }
                            showSearchModal = false
                        },
                        isOriginFocused = isOriginFocused,
                        vm = vm
                    )
                }
            }
        }
    )
}

@Composable
private fun PlaceSearchModal(
    onDismissRequest: () -> Unit,
    onPlaceSelected: (place: Place) -> Unit,
    isOriginFocused: Boolean,
    vm: ClientMapSearcherViewModel
) {
    val placePredictions by vm.placePredictions.collectAsState()
    var originSearchQuery by remember { mutableStateOf("") }
    var destinationSearchQuery by remember { mutableStateOf("") }
    val searchQueryState = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(isOriginFocused) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.75f)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(Color.Black),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "INTRODUCE TU RUTA",
                        modifier = Modifier.padding(start = 30.dp),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.padding(end = 30.dp),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                    )
                    {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }

                DefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(if (isOriginFocused) focusRequester else FocusRequester())
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                    value = originSearchQuery,
                    label = "Recoger en",
                    icon = Icons.Default.LocationOn,
                    onValueChange = {
                        originSearchQuery = it
                        if (isOriginFocused) {
                            searchQueryState.value = it
                        }
                    },
                    enabled = isOriginFocused,
                )
                DefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .background(if (!isOriginFocused) Color.Gray else Color.Transparent)
                        .focusRequester(if (!isOriginFocused) focusRequester else FocusRequester())
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                    value = destinationSearchQuery,
                    label = "Destino",
                    icon = Icons.Default.LocationOn,
                    onValueChange = {
                        destinationSearchQuery = it
                        if (!isOriginFocused) {
                            searchQueryState.value = it
                        }
                    },
                    enabled = !isOriginFocused,
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp)
                ) {
                    items(placePredictions) { prediction ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = prediction.fullText,
                                fontSize = 17.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        vm.getPlaceDetails(
                                            prediction.placeId,
                                            isOriginFocused
                                        ) { place ->
                                            onPlaceSelected(place)
                                            onDismissRequest()
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(searchQueryState.value) {
        Timber.d("SearchQuery: ${searchQueryState.value}")
        if (searchQueryState.value.isNotEmpty()) {
            delay(500)
            if (searchQueryState.value == if (isOriginFocused) originSearchQuery else destinationSearchQuery) {
                vm.getPlacePredictions(searchQueryState.value)
            }
        }
    }
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