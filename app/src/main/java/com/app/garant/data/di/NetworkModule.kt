package com.app.garant.data.di

import android.content.Context
import com.app.garant.data.pref.MyPref
import com.app.garant.utils.addHeaderInterceptor
//import com.app.garant.utils.addHeaderInterceptor
import com.app.garant.utils.addLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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