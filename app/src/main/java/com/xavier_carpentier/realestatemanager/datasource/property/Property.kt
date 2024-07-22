package com.xavier_carpentier.realestatemanager.datasource.property

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "property")
data class Property(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    val type: String,
    val price: Long,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val surface: Long,
    val room: Int,
    val bedroom: Int,
    val description: String,
    val entryDate: Date,
    val soldDate: Date,
    val sold: Boolean,
    val agent: String,
    val interestNearbySchool: Boolean,
    val interestNearbyShop: Boolean,
    val interestNearbyPark: Boolean,
    val interestNearbyRestaurant: Boolean,
    val interestNearbyPublicTransportation: Boolean,
    val interestNearbyPharmacy: Boolean
)
