package com.share.places.di

import com.share.places.feature.selectLocation.data.PositionData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListenersModule {

    @Provides
    @Singleton
    fun setAddressListener(): SharedFlow<PositionData?> = MutableStateFlow(null)
}