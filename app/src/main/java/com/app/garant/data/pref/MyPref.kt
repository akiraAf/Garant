package com.app.garant.data.pref

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPref @Inject constructor(private val context: Context) {

    private val pref = context.getSharedPreferences("cache", Context.MODE_PRIVATE)

    var phoneNumber: String
        set(value) {
            pref.edit().putString("phone", value).apply()
        }
        get() = pref.getString("phone", "")!!

    var authControll: Boolean
        set(value) {
            pref.edit().putBoolean("authControll", value).apply()
        }
        get() = pref.getBoolean("authControll", false)

    var name: String
        set(value) {
            pref.edit().putString("name", value).apply()
        }
        get() = pref.getString("name", "")!!
    var surname: String
        set(value) {
            pref.edit().putString("surname", value).apply()
        }
        get() = pref.getString("surname", "")!!

    var access_token: String
        set(value) {
            pref.edit().putString("access_token", value).apply()
        }
        get() = pref.getString("access_token", "")!!

    var startScreen: Boolean
        set(value) {
            pref.edit().putBoolean("startScreen", value).apply()
        }
        get() = pref.getBoolean("startScreen", false)

    var language: Boolean
        set(value) {
            pref.edit().putBoolean("language", value).apply()
        }
        get() = pref.getBoolean("language", true)!!

}