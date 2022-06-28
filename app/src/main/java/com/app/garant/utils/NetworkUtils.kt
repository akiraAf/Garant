package uz.softkomunal.a24seven.utils

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import uz.softkomunal.a24seven.BuildConfig
import uz.softkomunal.a24seven.data.pref.MyPref

fun OkHttpClient.Builder.addLoggingInterceptor(context: Context): OkHttpClient.Builder {
    HttpLoggingInterceptor.Level.HEADERS
    val logging = HttpLoggingInterceptor.Logger { message -> Timber.tag("HTTP").d(message) }
    if (BuildConfig.LOGGING) {
        addInterceptor(ChuckInterceptor(context))
            .addInterceptor(HttpLoggingInterceptor(logging))
    }
    return this
}

fun addHeaderInterceptor(pref: MyPref) = Interceptor { chain ->
    val request = chain.request()
    val newRequest = request.newBuilder().removeHeader("Authorization").addHeader("Authorization", "Bearer " + pref.access_token).build()
    val response = chain.proceed(newRequest)
    response
}

