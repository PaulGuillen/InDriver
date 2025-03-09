package com.devpaul.indriver.domain.model.res

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val message: String,
    val statusCode: Int,
)