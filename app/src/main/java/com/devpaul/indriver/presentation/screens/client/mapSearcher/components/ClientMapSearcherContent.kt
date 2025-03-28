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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devpaul.indriver.R
import com.devpaul.indriver.presentation.components.DefaultButton
import com.devpaul.indriver.presentation.components.DefaultTextField
import com.devpaul.indriver.presentation.screens.client.ClientMapSearcherViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.libraries.places.api.model.Place
import com.google.maps.android.compose.CameraMoveStartedReason
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Polyline
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
    var priceQuery by remember { mutableStateOf("") }
    val location by vm.location.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    var isCameraCentered by remember { mutableStateOf(false) }
    var showSearchModal by remember { mutableStateOf(false) }
    var showPriceModal by remember { mutableStateOf(false) }
    var isOriginFocused by remember { mutableStateOf(false) }
    val originPlace by vm.originPlace.collectAsState()
    val route by vm.route.collectAsState()

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style),
                isMyLocationEnabled = true,
            )
        )
    }

    LaunchedEffect(key1 = originPlace) {
        originPlace?.let {
            originQuery = it.address
        }
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
        scaffoldState = rememberBottomSheetScaffoldState(),
        sheetPeekHeight = calculateSheetHeight(vm = vm),
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
                            .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                            .clickable {
                                isOriginFocused = true
                                showSearchModal = true
                            },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(start = 5.dp),
                                imageVector = Icons.Filled.Search,
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = originQuery.ifEmpty { "Recoger en...." },
                                color = Color(0xFF3A3939)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp)
                            .clickable {
                                isOriginFocused = false
                                showSearchModal = true
                            },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(start = 5.dp),
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = destinationQuery.ifEmpty { "Destino" },
                                color = Color(0xFF3A3939)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Card(
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp)
                            .clickable {
                                showPriceModal = true // MOSTRAR EL  MODAL DE BUSQUEDA
                            },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(start = 5.dp),
                                imageVector = Icons.Default.Info,
                                contentDescription = null
                            )
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = priceQuery.ifEmpty { "Ofertar precio" },
                                color = Color(0xFF3A3939)
                            )
                        }
                    }
                    DefaultButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp, top = 20.dp),
                        text = "Buscar Conductor",
                        onClick = { /*TODO*/ }
                    )
                }
            }
        },
        content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box {
                    GoogleMap(
                        modifier = Modifier
                            .padding(paddingValues),
                        cameraPositionState = cameraPositionState,
                        properties = mapProperties,

                        ) {

                        route?.let { routePoints ->
                            Polyline(
                                points = routePoints,
                                color = Color.Green,
                                width = 14f,
                            )

                        }
//                        location.let { position ->
//                            if (position != null) {
//                                Marker(
//                                    state = MarkerState(position = position)
//                                )
//                            }
//
//                        }
                    }
                    Icon(
                        modifier = Modifier
                            .size(50.dp)
                            .align(alignment = Alignment.Center),
                        painter = painterResource(id = R.drawable.pin),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }

                CheckForMapInteraction(cameraPositionState = cameraPositionState, vm = vm)
                if (showSearchModal) {
                    PlaceSearchModal(
                        onDismissRequest = { showSearchModal = false },
                        onPlaceSelected = { place ->
                            if (isOriginFocused) {
                                originQuery = place.address
                                cameraPositionState.position =
                                    CameraPosition.fromLatLngZoom(place.latLng, 14f)
                            } else {
                                destinationQuery = place.address
                            }

                            if (!originQuery.isNullOrBlank() && !destinationQuery.isNullOrBlank()) {
                                vm.getRoute()
                            }

                            showSearchModal = false
                        },
                        isOriginFocused = isOriginFocused,
                        vm = vm
                    )
                }
                if (showPriceModal) {
                    PriceModal(
                        onDismissRequest = { showPriceModal = false },
                        onPriceSelected = { price ->
                            priceQuery = price
                            showPriceModal = false
                        }
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
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.30f)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .background(Color.Black),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Introduce tu ruta",
                        modifier = Modifier.padding(start = 24.dp),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.padding(end = 10.dp),
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
                    icon = Icons.Filled.LocationOn,
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
private fun PriceModal(
    onDismissRequest: () -> Unit,
    onPriceSelected: (string: String) -> Unit
) {
    var priceQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
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
                .fillMaxHeight(fraction = 0.30f)
                .padding(start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(20.dp))
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
                        text = "INTRODUCE TU OFERTA",
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
                        .focusRequester(focusRequester)
                        .padding(top = 20.dp, start = 30.dp, end = 30.dp),
                    value = priceQuery,
                    label = "Valor de oferta",
                    icon = Icons.Default.Info,
                    onValueChange = {
                        priceQuery = it
                    },
                    keyboardType = KeyboardType.Number,
                    background = Color(0xFFEEEEEE)
                )
                Spacer(modifier = Modifier.weight(1f))
                DefaultButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, bottom = 20.dp),
                    text = "Asignar Oferta",
                    onClick = {
                        onPriceSelected(priceQuery)
                        onDismissRequest()
                    }
                )
            }
        }
    }

}

@Composable
private fun calculateSheetHeight(vm: ClientMapSearcherViewModel): Dp {
    val normalHeight = LocalConfiguration.current.screenHeightDp.dp * 0.42f
    val minimizedHeight = 0.dp
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
            vm.getPlaceFromLatLng(newCameraPosition.target)
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