package com.share.places.di

import com.share.places.feature.selectLocation.data.PositionData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListenersModule {

    @Provides
    @Singleton
    fun setAddressListener(): MutableSharedFlow<PositionData> = MutableSharedFlow()
}