package com.devpaul.indriver.data.util

import com.google.android.gms.maps.model.LatLng

object PolylineDrawer {

    fun decodePoly(encoded: String): List<LatLng> {
        val poly = mutableListOf<LatLng>()
        var index = 0
        val length = encoded.length
        var lat = 0
        var lng = 0

        while (index < length) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) {
                // Handle negative latitude
                -(result shr 1)
            } else {
                result shr 1
            }
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or ((b and 0x1f) shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) {
                // Handle negative longitude
                -(result shr 1)
            } else {
                result shr 1
            }
            lng += dlng

            // Convert to latitude and longitude
            val p = LatLng(
                (lat / 1E5).toDouble(),
                (lng / 1E5).toDouble()
            )
            poly.add(p)
        }
        return poly
    }

}