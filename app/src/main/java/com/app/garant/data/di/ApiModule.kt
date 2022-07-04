package com.app.garant.data.di

import com.app.garant.data.api.AuthApi
import com.app.garant.data.api.CategoryApi
import com.app.garant.data.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @[Provides Singleton]
    fun getAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @[Provides Singleton]
    fun getUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @[Provides Singleton]
    fun getCategoryApi(retrofit: Retrofit): CategoryApi = retrofit.create(CategoryApi::class.java)


}