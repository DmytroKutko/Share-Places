package com.places.network.di

import com.google.firebase.database.FirebaseDatabase
import com.places.network.geo.GeoRepository
import com.places.network.geo.GeoRepositoryImpl
import com.places.network.geo.GeocodingApi
import com.places.network.utils.Constants.GEO_BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

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

    @Provides
    @Singleton
    fun provideGeoRepository(api: GeocodingApi): GeoRepository = GeoRepositoryImpl(api = api)
}