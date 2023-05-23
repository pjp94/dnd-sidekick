package com.pancholi.grabbag.hilt.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object GsonModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}