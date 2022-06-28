package com.app.garant.data.di

import android.content.Context
import com.app.garant.data.api.AuthApi
import com.app.garant.data.pref.MyPref
import com.app.garant.data.request.auth.LoginRequest
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import uz.softkomunal.a24seven.utils.addHeaderInterceptor
import uz.softkomunal.a24seven.utils.addLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @[Provides Singleton]
    fun getRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
//            .baseUrl(BASE_URL)
            .baseUrl("https://garant-mobile.usoftdev.uz/")
            .build()


    @[Provides Singleton]
    fun getOkHttpClient(pref: MyPref, @ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .addLoggingInterceptor(context)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(addHeaderInterceptor(pref))
            .build()

}