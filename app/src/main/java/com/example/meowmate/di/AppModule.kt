package com.example.meowmate.di

import com.example.meowmate.BuildConfig

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiKeyProvider(): () -> String? = { BuildConfig.CAT_API_KEY }
}