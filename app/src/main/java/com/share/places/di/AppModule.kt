package com.share.places.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.share.places.R
import com.share.places.api.geo_api.GeocodingApi
import com.share.places.feature.selectLocation.domain.ChooseLocationUseCases
import com.share.places.feature.selectLocation.domain.GetAddress
import com.share.places.utils.Constants.GEO_BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
    fun moshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun geoApi(moshi: Moshi): GeocodingApi = Retrofit.Builder()
        .baseUrl(GEO_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(GeocodingApi::class.java)

    @Singleton
    @Provides
    fun createPlaceUseCases(api: GeocodingApi, @ApplicationContext context: Context): ChooseLocationUseCases = ChooseLocationUseCases(
        GetAddress(api, context.getString(R.string.google_maps_geo_key)),
    )
}