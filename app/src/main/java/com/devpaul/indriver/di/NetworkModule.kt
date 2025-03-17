package com.devpaul.indriver.di

import com.devpaul.indriver.core.BaseUrlInterceptor
import com.devpaul.indriver.core.Config
import com.devpaul.indriver.data.local.datastore.LocalDataStore
import com.devpaul.indriver.data.remote.datasource.remote.service.AuthService
import com.devpaul.indriver.data.remote.datasource.remote.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

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
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}