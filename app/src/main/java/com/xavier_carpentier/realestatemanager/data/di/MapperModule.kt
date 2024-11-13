package com.xavier_carpentier.realestatemanager.data.di

import com.xavier_carpentier.realestatemanager.data.agent.AgentMapper
import com.xavier_carpentier.realestatemanager.data.currency.CurrencyDataMapper
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
}