package com.app.garant.data.di

import com.app.garant.domain.repository.AuthRepository
import com.app.garant.domain.repository.CategoryRepository
import com.app.garant.domain.repository.UserRepository
import com.app.garant.domain.repositoryimpl.AuthRepositoryImpl
import com.app.garant.domain.repositoryimpl.CategoryRepositoryImpl
import com.app.garant.domain.repositoryimpl.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun getAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun getUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun getCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository


}