package com.devpaul.indriver.core

import com.devpaul.indriver.data.datasource.local.datastore.LocalDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class BaseUrlInterceptor(private val localDataStore: LocalDataStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrl = request.url

        val token = runBlocking { localDataStore.getData().first().token ?: "" }

        val newUrl = originalUrl.newBuilder()
            .encodedPath(originalUrl.encodedPath)
            .query(originalUrl.query)
            .build()

        val newRequestBuilder = request.newBuilder()
            .url(newUrl)

        if (token.isNotBlank()) {
            newRequestBuilder.addHeader("Authorization",  token)
        }

        return chain.proceed(newRequestBuilder.build())
    }
}
