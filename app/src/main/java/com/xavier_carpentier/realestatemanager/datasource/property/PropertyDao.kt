package com.xavier_carpentier.realestatemanager.datasource.property

import android.database.Cursor
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
    suspend fun insert(property:Property):Long

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
    fun getAllPropertyAndPicture(): Flow<List<PropertyWithPicture>>

    @Transaction
    @Query("""
    SELECT * FROM property 
    WHERE price BETWEEN :minPrice AND :maxPrice
      AND surface BETWEEN :minSurface AND :maxSurface
      AND (:sold IS NULL OR sold = :sold)
      AND (:nearbySchool = 0 OR interestNearbySchool = 1)
      AND (:nearbyShop = 0 OR interestNearbyShop = 1)
      AND (:nearbyPark = 0 OR interestNearbyPark = 1)
      AND (:nearbyRestaurant = 0 OR interestNearbyRestaurant = 1)
      AND (:nearbyPublicTransportation = 0 OR interestNearbyPublicTransportation = 1)
      AND (:nearbyPharmacy = 0 OR interestNearbyPharmacy = 1)
""")
    fun getFilteredPropertiesNoTypes(
        minPrice: Long, maxPrice: Long,
        minSurface: Long, maxSurface: Long,
        sold: Boolean?,
        nearbySchool: Boolean,
        nearbyShop: Boolean,
        nearbyPark: Boolean,
        nearbyRestaurant: Boolean,
        nearbyPublicTransportation: Boolean,
        nearbyPharmacy: Boolean
    ): Flow<List<PropertyWithPicture>>

    @Transaction
    @Query("""
    SELECT * FROM property 
    WHERE type IN (:types)
      AND price BETWEEN :minPrice AND :maxPrice
      AND surface BETWEEN :minSurface AND :maxSurface
      AND (:sold IS NULL OR sold = :sold)
      AND (:nearbySchool = 0 OR interestNearbySchool = 1)
      AND (:nearbyShop = 0 OR interestNearbyShop = 1)
      AND (:nearbyPark = 0 OR interestNearbyPark = 1)
      AND (:nearbyRestaurant = 0 OR interestNearbyRestaurant = 1)
      AND (:nearbyPublicTransportation = 0 OR interestNearbyPublicTransportation = 1)
      AND (:nearbyPharmacy = 0 OR interestNearbyPharmacy = 1)
""")
    fun getFilteredPropertiesWithTypes(
        types: List<String>,
        minPrice: Long, maxPrice: Long,
        minSurface: Long, maxSurface: Long,
        sold: Boolean?,
        nearbySchool: Boolean,
        nearbyShop: Boolean,
        nearbyPark: Boolean,
        nearbyRestaurant: Boolean,
        nearbyPublicTransportation: Boolean,
        nearbyPharmacy: Boolean
    ): Flow<List<PropertyWithPicture>>

    @Query("SELECT * FROM property")
    fun getAllPropertyCursor(): Cursor

    @Query("SELECT * FROM property WHERE id = :id")
    fun getPropertyCursor(id: Int): Cursor
}