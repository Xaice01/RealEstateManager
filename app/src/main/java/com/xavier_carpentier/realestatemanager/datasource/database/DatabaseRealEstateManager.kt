package com.xavier_carpentier.realestatemanager.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xavier_carpentier.realestatemanager.datasource.picture.Picture
import com.xavier_carpentier.realestatemanager.datasource.picture.PictureDao
import com.xavier_carpentier.realestatemanager.datasource.property.Property
import com.xavier_carpentier.realestatemanager.datasource.property.PropertyDao
import com.xavier_carpentier.realestatemanager.datasource.utils.CalendarToLongConverter

@Database(entities = [Property::class, Picture::class], version = 1, exportSchema=false)
@TypeConverters(CalendarToLongConverter::class)
abstract class DatabaseRealEstateManager() :RoomDatabase() {

    abstract fun propertyDao() :PropertyDao
    abstract fun pictureDao() : PictureDao

}