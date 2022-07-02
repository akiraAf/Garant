package com.app.garant.utils

import android.content.Context
import com.app.garant.BuildConfig
import com.app.garant.data.pref.MyPref
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

fun OkHttpClient.Builder.addLoggingInterceptor(context: Context): OkHttpClient.Builder {
    HttpLoggingInterceptor.Level.HEADERS
    val logging = HttpLoggingInterceptor.Logger { message -> Timber.tag("HTTP").d(message) }
    if (BuildConfig.LOGGING) {
        addInterceptor(ChuckInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor(logging))
    }
    return this
}




