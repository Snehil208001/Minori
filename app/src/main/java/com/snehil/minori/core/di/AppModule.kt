package com.snehil.minori.core.di

import com.snehil.minori.data.repository.AuthRepositoryImpl
import com.snehil.minori.data.repository.ProductRepositoryImpl
import com.snehil.minori.domain.repository.AuthRepository
import com.snehil.minori.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
}
