package com.app.garant.data.di

import android.content.Context
import com.app.garant.data.pref.MyPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @[Provides Singleton]
    fun getSharedPreferences(@ApplicationContext context: Context): MyPref = MyPref(context)

}