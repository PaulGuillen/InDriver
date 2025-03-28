package com.devpaul.indriver.domain.model.res

import com.google.gson.annotations.SerializedName

data class DirectionsResponse(
    @SerializedName("routes")
    val routes: List<Route>,
)

data class Route(
    @SerializedName("overview_polyline")
    val overViewPolyline: OverViewPolyline,
)

data class OverViewPolyline(
    @SerializedName("points")
    val points: String
)