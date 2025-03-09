package com.devpaul.indriver.domain.util

import com.devpaul.indriver.domain.model.res.ErrorResponse
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber

object ErrorHelper {
    fun handleError(errorBody: ResponseBody?): ErrorResponse? {
        return try {
            errorBody?.source()?.let {
                val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                val moshiAdapter = moshi.adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (e: Exception) {
            Timber.e(e)
            ErrorResponse("Error desconocido", 0)
        }
    }
}