package com.xavier_carpentier.realestatemanager.data.di

import android.content.Context
import androidx.room.Room
import com.xavier_carpentier.realestatemanager.data.picture.OfflinePictureRepository
import com.xavier_carpentier.realestatemanager.data.property.OfflinePropertyRepository
import com.xavier_carpentier.realestatemanager.datasource.database.DatabaseRealEstateManager
import com.xavier_carpentier.realestatemanager.datasource.picture.PictureDao
import com.xavier_carpentier.realestatemanager.datasource.property.PropertyDao
import com.xavier_carpentier.realestatemanager.domain.picture.PictureRepository
import com.xavier_carpentier.realestatemanager.domain.property.PropertyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun ProvideDatabaseRealEstateManager(@ApplicationContext context: Context) : DatabaseRealEstateManager{
        return Room.databaseBuilder(context,DatabaseRealEstateManager::class.java,"RealEstateManager_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePropertyDao(database :DatabaseRealEstateManager):PropertyDao{
        return database.propertyDao()
    }

    @Provides
    fun providePictureDao(database :DatabaseRealEstateManager): PictureDao {
        return database.pictureDao()
    }

    @Provides
    fun providePropertyRepository(propertyDao: PropertyDao):PropertyRepository{
        return OfflinePropertyRepository(propertyDao)
    }

    @Provides
    fun providePictureRepository(pictureDao: PictureDao) :PictureRepository{
        return OfflinePictureRepository(pictureDao)
    }
}