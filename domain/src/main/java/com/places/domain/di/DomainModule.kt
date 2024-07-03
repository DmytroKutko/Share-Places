package com.places.domain.di

import com.places.domain.delegates.place.GetAllPlaces
import com.places.domain.delegates.place.InsertPlace
import com.places.domain.delegates.place.PlaceUseCases
import com.places.domain.selectLocation.ChooseLocationUseCases
import com.places.domain.selectLocation.GetAddress
import com.places.network.firebase.PlaceRepository
import com.places.network.geo.GeoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideLocationUseCases(repository: GeoRepository): ChooseLocationUseCases =
        ChooseLocationUseCases(
            GetAddress(repository),
        )

    @Singleton
    @Provides
    fun providePlaceUseCases(repository: PlaceRepository): PlaceUseCases =
        PlaceUseCases(
            getAllPlaces = GetAllPlaces(repository),
            insertPlace = InsertPlace(repository)
        )
}