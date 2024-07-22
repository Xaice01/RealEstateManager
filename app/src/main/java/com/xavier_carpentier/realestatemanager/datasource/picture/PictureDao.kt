package com.xavier_carpentier.realestatemanager.datasource.picture

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface PictureDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(picture: Picture)

    @Update
    suspend fun update(picture: Picture)

    @Delete
    suspend fun delete(picture: Picture)

    @Query("SELECT * from picture WHERE id= :id")
    fun getPicture(id :Int): Flow<Picture?>

    @Query("SELECT * from picture WHERE propertyId= :propertyId")
    fun getPicturesByProperty(propertyId :Int) :Flow<List<Picture>?>

    @Query("SELECT * from picture ORDER BY id ASC")
    fun getAllPicture() : Flow<List<Picture>>
}