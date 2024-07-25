package com.xavier_carpentier.realestatemanager.datasource.property

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
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

    @Transaction
    @Query("SELECT * From property Where id= :id")
    fun getPropertyAndPicture(id :Int): Flow<PropertyWithPicture?>

    @Transaction
    @Query("SELECT * From property")
    fun getAllPropertyAndPicture(): Flow<PropertyWithPicture>

}