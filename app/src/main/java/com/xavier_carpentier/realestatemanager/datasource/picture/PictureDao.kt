package com.xavier_carpentier.realestatemanager.datasource.picture

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(picture: Picture):Long

    @Update
    suspend fun update(picture: Picture)

    @Upsert
    suspend fun upsert(picture: Picture)

    @Delete
    suspend fun delete(picture: Picture)

    @Query("SELECT * from picture WHERE id= :id")
    fun getPicture(id :Int): Flow<Picture?>

    @Query("SELECT * from picture WHERE propertyId= :propertyId")
    fun getPicturesByProperty(propertyId :Int) :Flow<List<Picture>?>

    @Query("SELECT * from picture ORDER BY id ASC")
    fun getAllPicture() : Flow<List<Picture>>

    @Query("SELECT * FROM picture")
    fun getAllPictureCursor(): Cursor
}