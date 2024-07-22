package com.xavier_carpentier.realestatemanager.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xavier_carpentier.realestatemanager.datasource.picture.Picture
import com.xavier_carpentier.realestatemanager.datasource.picture.PictureDao
import com.xavier_carpentier.realestatemanager.datasource.property.Property
import com.xavier_carpentier.realestatemanager.datasource.property.PropertyDao

@Database(entities = [Property::class, Picture::class], version = 1, exportSchema=false)
abstract class DatabaseRealEstateManager() :RoomDatabase() {

    abstract fun propertyDao() :PropertyDao
    abstract fun pictureDao() : PictureDao

    companion object{
        //singleton
        @Volatile
        private var instance :DatabaseRealEstateManager?=null

        fun getDatabase(context : Context) :DatabaseRealEstateManager{
            return instance ?: synchronized(this){
                Room.databaseBuilder(context,DatabaseRealEstateManager::class.java,"RealEstateManager_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}