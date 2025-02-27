package com.xavier_carpentier.realestatemanager.data.di

import com.xavier_carpentier.realestatemanager.data.agent.AgentMapper
import com.xavier_carpentier.realestatemanager.data.currency.CurrencyDataMapper
import com.xavier_carpentier.realestatemanager.data.filter.model.FilterMapper
import com.xavier_carpentier.realestatemanager.data.property_type.PropertyTypeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}