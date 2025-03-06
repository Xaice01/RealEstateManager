package com.xavier_carpentier.realestatemanager.data.di

import com.xavier_carpentier.realestatemanager.data.agent.AgentRepositoryImpl
import com.xavier_carpentier.realestatemanager.data.current_property.CurrentPropertyRepositoryImpl
import com.xavier_carpentier.realestatemanager.data.current_setting.CurrentSettingRepositoryImpl
import com.xavier_carpentier.realestatemanager.data.filter.PropertiesFilterRepositoryImpl
import com.xavier_carpentier.realestatemanager.data.location.LocationRepositoryImpl
import com.xavier_carpentier.realestatemanager.data.permission.PermissionLocationRepositoryImpl
import com.xavier_carpentier.realestatemanager.data.property_type.PropertyTypeRepositoryImpl
import com.xavier_carpentier.realestatemanager.domain.agent.AgentRepository
import com.xavier_carpentier.realestatemanager.domain.current_property.CurrentPropertyRepository
import com.xavier_carpentier.realestatemanager.domain.current_setting.CurrentSettingRepository
import com.xavier_carpentier.realestatemanager.domain.filter.FilterRepository
import com.xavier_carpentier.realestatemanager.domain.location.LocationRepository
import com.xavier_carpentier.realestatemanager.domain.permission.PermissionLocationRepository
import com.xavier_carpentier.realestatemanager.domain.property_type.PropertyTypeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {

    @Singleton
    @Binds
    abstract fun bindCurrentPropertyRepository(implementation: CurrentPropertyRepositoryImpl): CurrentPropertyRepository

    @Singleton
    @Binds
    abstract fun bindCurrentSettingRepository(implementation: CurrentSettingRepositoryImpl): CurrentSettingRepository

    @Singleton
    @Binds
    abstract fun bindAgentRepositoryRepository(implementation: AgentRepositoryImpl): AgentRepository

    @Singleton
    @Binds
    abstract fun bindPropertyTypeRepository(implementation: PropertyTypeRepositoryImpl): PropertyTypeRepository

    @Singleton
    @Binds
    abstract fun bindFilterRepository(implementation: PropertiesFilterRepositoryImpl): FilterRepository

    @Singleton
    @Binds
    abstract fun bindLocationRepository(implementation: LocationRepositoryImpl): LocationRepository

    @Singleton
    @Binds
    abstract fun bindPermissionLocationRepository(implementation: PermissionLocationRepositoryImpl): PermissionLocationRepository

}