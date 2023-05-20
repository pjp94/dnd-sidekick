package com.pancholi.grabbag.hilt.module

import android.content.Context
import androidx.room.Room
import com.pancholi.grabbag.database.GrabBagDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): GrabBagDatabase {
        return Room.databaseBuilder(
            appContext,
            GrabBagDatabase::class.java,
            "GrabBagDatabase"
        ).build()
    }
}