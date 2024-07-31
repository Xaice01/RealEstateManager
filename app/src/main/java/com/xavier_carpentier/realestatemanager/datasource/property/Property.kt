package com.xavier_carpentier.realestatemanager.datasource.property

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "property")
data class Property(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    val type: String,
    //price in Dollar
    val price: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    //Surface in metre carré
    val surface: Long,
    val room: Int,
    val bedroom: Int,
    val description: String,
    val entryDate: Calendar = Calendar.getInstance(),
    val soldDate: Calendar?,
    val sold: Boolean,
    val agent: String,
    val interestNearbySchool: Boolean,
    val interestNearbyShop: Boolean,
    val interestNearbyPark: Boolean,
    val interestNearbyRestaurant: Boolean,
    val interestNearbyPublicTransportation: Boolean,
    val interestNearbyPharmacy: Boolean
)
