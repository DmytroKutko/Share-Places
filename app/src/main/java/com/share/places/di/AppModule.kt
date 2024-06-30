package com.share.places.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.places.network.geo.GeoRepository
import com.share.places.BuildConfig
import com.share.places.feature.selectLocation.domain.ChooseLocationUseCases
import com.share.places.feature.selectLocation.domain.GetAddress
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = Firebase.database

    @Singleton
    @Provides
    fun createPlaceUseCases(repository: GeoRepository): ChooseLocationUseCases = ChooseLocationUseCases(
        GetAddress(repository),
    )
}