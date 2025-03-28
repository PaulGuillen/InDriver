package com.devpaul.indriver.di

import com.devpaul.indriver.core.BaseUrlInterceptor
import com.devpaul.indriver.core.Config
import com.devpaul.indriver.data.datasource.local.datastore.LocalDataStore
import com.devpaul.indriver.data.datasource.remote.AuthService
import com.devpaul.indriver.data.datasource.remote.GoogleMapService
import com.devpaul.indriver.data.datasource.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GoogleMapsRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(dataStore: LocalDataStore): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(
            BaseUrlInterceptor(dataStore)
        )
            .build()

    @Provides
    @Singleton
    @DefaultRetrofit
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @GoogleMapsRetrofit
    fun provideGoogleRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.BASE_GOOGLE_MAPS_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGoogleMapService(@GoogleMapsRetrofit retrofit: Retrofit): GoogleMapService {
        return retrofit.create(GoogleMapService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthService(@DefaultRetrofit retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(@DefaultRetrofit retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}