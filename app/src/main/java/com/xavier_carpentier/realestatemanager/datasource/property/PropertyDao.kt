package com.xavier_carpentier.realestatemanager.datasource.property

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.xavier_carpentier.realestatemanager.datasource.picture.Picture
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(property:Property)

    @Update
    suspend fun update(property: Property)

    @Delete
    suspend fun delete(property: Property)

    @Query("SELECT * from property WHERE id= :id")
    fun getProperty(id :Int): Flow<Property>

    @Query("SELECT * from property ORDER BY sold ASC")
    fun getAllProperty() :Flow<List<Property>>

    @Query("SELECT * From property JOIN Picture ON Property.id = Picture.propertyId Where id= :id ORDER BY sold ASC")
    fun getPropertyAndPicture(id : Int): Flow<Map<Property, List<Picture>>>

    @Query("SELECT * From property JOIN Picture ON Property.id = Picture.propertyId")
    fun getAllPropertyAndPicture(): Flow<List<Map<Property, List<Picture>>>>

}