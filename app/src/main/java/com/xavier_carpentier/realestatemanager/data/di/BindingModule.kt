package com.xavier_carpentier.realestatemanager.data.di

import com.xavier_carpentier.realestatemanager.domain.current_property.CurrentPropertyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.delcey.openclassrooms_master_detail_mvvm.data.current_mail.CurrentPropertyRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {

    @Singleton
    @Binds
    abstract fun bindCurrentPropertyRepository(implementation: CurrentPropertyRepositoryImpl): CurrentPropertyRepository

}