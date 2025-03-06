package com.xavier_carpentier.realestatemanager.data.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.xavier_carpentier.realestatemanager.data.agent.AgentMapper
import com.xavier_carpentier.realestatemanager.data.currency.CurrencyDataMapper
import com.xavier_carpentier.realestatemanager.data.filter.model.FilterMapper
import com.xavier_carpentier.realestatemanager.data.property_type.PropertyTypeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideAgentMapper(): AgentMapper {
        return AgentMapper()
    }

    @Provides
    @Singleton
    fun provideCurrencyDataMapper() : CurrencyDataMapper {
        return CurrencyDataMapper()
    }

    @Provides
    @Singleton
    fun providePropertyTypeMapper(): PropertyTypeMapper {
        return PropertyTypeMapper()
    }

    @Provides
    @Singleton
    fun provideFilterMapper(): FilterMapper {
        return FilterMapper()
    }

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

}